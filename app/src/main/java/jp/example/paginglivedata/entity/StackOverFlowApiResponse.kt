package jp.example.paginglivedata.entity

data class Owner(
    val reputation: Int,
    val user_id: Long,
    val user_type: String,
    val profile_image: String,
    val display_name: String,
    val link: String
)

data class Item(
    var owner: Owner?,
    var is_accepted: Boolean,
    var score: Int,
    var last_activity_date: Long,
    var creation_date: Long,
    var answer_id: Long,
    var question_id: Long
)

data class StackApiResponse(
    var items: List<Item>,
    var has_more: Boolean,
    var quota_max: Int,
    var quota_remaining: Int
)
