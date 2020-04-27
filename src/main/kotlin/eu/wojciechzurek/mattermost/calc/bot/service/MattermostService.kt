package eu.wojciechzurek.mattermost.calc.bot.service

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.*
import io.reactivex.Single

interface MattermostService {
    fun post(body: Any)
    fun user(id: String): Single<User>
    fun getUserImageEndpoint(userId: String): String
    fun getFileEndpoint(fileId: String): String
    fun getFilePreviewEndpoint(fileId: String): String
    fun ephemeralPost(body: Any)

}