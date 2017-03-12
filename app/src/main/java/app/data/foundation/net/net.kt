/*
 * Copyright 2017 Victor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.data.foundation.net

import app.data.foundation.net.NetModule.jolyglot
import com.google.gson.JsonSyntaxException
import io.victoralbertos.jolyglot.GsonSpeaker
import io.victoralbertos.mockery.api.built_in_interceptor.ErrorResponseAdapter

/**
 * Resolve dependencies state with a global (aka singleton) scope
 */
object NetModule {
    val jolyglot = GsonSpeaker()
}

data class ResponseError(val message: String)

/**
 * Prettify server error message.
 */
fun adaptError(json : String) : String {
    try {
        return jolyglot
                .fromJson(json, ResponseError::class.java)
                .message
    } catch (e: JsonSyntaxException) {
        return json
    }
}

/**
 * Required by mockery framework
 */
class ErrorResponseAdapterMockery : ErrorResponseAdapter {
    override fun adapt(error: String?): String = adaptError(adapt(error))
}