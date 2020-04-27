package eu.wojciechzurek.mattermost.calc.bot.service

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Post
import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.User
import eu.wojciechzurek.mattermost.calc.bot.loggerFor
import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriTemplate
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class MattermostServiceImpl(
        @Client("\${mattermost.api.endpoint}") private val webClient: RxHttpClient,
        @Value("\${mattermost.key}") private val apiKey: String,
        @Value("\${mattermost.api.endpoint}") private val apiEndpoint: String
) : MattermostService {

    private val logger = loggerFor(this.javaClass)

    override fun getUserImageEndpoint(userId: String): String = apiEndpoint.plus("/users/$userId/image")

    override fun getFileEndpoint(fileId: String): String = apiEndpoint.plus("/files/$fileId")

    override fun getFilePreviewEndpoint(fileId: String): String = apiEndpoint.plus("/files/$fileId/preview")

    override fun post(body: Any) {
        val request = HttpRequest.POST("/posts", body)
        post(request)
    }

    override fun ephemeralPost(body: Any) {
        val request = HttpRequest.POST("/posts/ephemeral", body)
        post(request)
    }

    private fun post(request: HttpRequest<Any>) {
        webClient.retrieve(request, Post::class.java).firstOrError()
                .doOnError {
                    logger.error(it.message)
                }
                .subscribe { post ->
                    logger.info("Post: {}", post)
                }
    }

    override fun user(id: String): Single<User> {
        val path = "/users/{id}"
        val uri = UriTemplate.of(path).expand(mapOf("id" to id))

        val request = HttpRequest.GET<Any>(uri)
        request.parameters

        return webClient.retrieve(request, User::class.java).firstOrError()
                .doOnError {
                    logger.error(it.message)
                }
                .doOnSuccess { logger.info("User: {}", it) }
    }
}