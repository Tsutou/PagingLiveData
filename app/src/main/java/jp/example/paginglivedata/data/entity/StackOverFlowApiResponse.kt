package jp.example.paginglivedata.data.entity

/**
 * Owner Object
 */
data class Owner(
    val reputation: Int,
    val user_id: Long,
    val user_type: String,
    val profile_image: String,
    val display_name: String,
    val link: String
)

/**
 * Item Object
 */
data class Item(
    val owner: Owner,
    val is_accepted: Boolean,
    val score: Int,
    val last_activity_date: Long,
    val creation_date: Long,
    val answer_id: Long,
    val question_id: Long
)

/**
 * StackApiResponse Object
 * 全体をラップするルートオブジェクト
 * @property items Paging対象のitemのコレクション
 * @property has_more 次のページがあるかどうか判定するフラグ
 */
data class StackApiResponse(
    val items: List<Item>,
    val has_more: Boolean,
    val quota_max: Int,
    val quota_remaining: Int
)
