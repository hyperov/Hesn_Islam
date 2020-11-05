package com.islam.hesn.myapplication.youtube.model.response

open class CommonVideo(
) {
    lateinit var snippet: Snippet
    override fun equals(other: Any?): Boolean {
        return this === other
    }
}

//"id": {
//            "kind": "youtube#video",
//            "videoId": "slSXQ3Cit2c"
//           },