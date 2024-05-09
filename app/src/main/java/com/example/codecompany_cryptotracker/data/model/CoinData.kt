package com.example.codecompany_cryptotracker.data.model

import com.google.gson.annotations.SerializedName
// Data Class for Coin Information from CoinGecko API
// Coin TradeUrl
data class CoinData(
    val id: String?,
    val symbol: String?,
    val name: String,
    @SerializedName("web_slug")
    val webSlug: String,
    @SerializedName("asset_platform_id")
    val assetPlatformId: Any?, // Change the type accordingly if you know the type
    val platforms: Map<String, String>,
    @SerializedName("detail_platforms")
    val detailPlatforms: Map<String, DetailPlatform>,
    @SerializedName("block_time_in_minutes")
    val blockTimeInMinutes: Int,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String,
    val categories: List<String>,
    @SerializedName("preview_listing")
    val previewListing: Boolean,
    val publicNotice: Any?, // Change the type accordingly if you know the type
    @SerializedName("additional_notices")
    val additionalNotices: List<Any>, // Change the type accordingly if you know the type
    val description: Description,
    val links: Links?,
    val image: Image?,
    @SerializedName("country_origin")
    val countryOrigin: String,
    @SerializedName("genesis_date")
    val genesisDate: String,
    @SerializedName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double,
    @SerializedName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double,
    @SerializedName("watchlist_portfolio_users")
    val watchlistPortfolioUsers: Int,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("community_data")
    val communityData: CommunityData?,
    @SerializedName("developer_data")
    val developerData: DeveloperData?,
    @SerializedName("status_updates")
    val statusUpdates: List<Any>, // Change the type accordingly if you know the type
    @SerializedName("last_updated")
    val lastUpdated: String
)

data class DetailPlatform(
    @SerializedName("decimal_place")
    val decimalPlace: Any?, // Change the type accordingly if you know the type
    @SerializedName("contract_address")
    val contractAddress: String
)

data class Description(
    val en: String
)

data class Links(
    val homepage: List<String>,
    val whitepaper: String,
    @SerializedName("blockchain_site")
    val blockchainSite: List<String>,
    @SerializedName("official_forum_url")
    val officialForumUrl: List<String>,
    @SerializedName("chat_url")
    val chatUrl: List<String>,
    @SerializedName("announcement_url")
    val announcementUrl: List<String>,
    @SerializedName("twitter_screen_name")
    val twitterScreenName: String,
    @SerializedName("facebook_username")
    val facebookUsername: String,
    @SerializedName("bitcointalk_thread_identifier")
    val bitcointalkThreadIdentifier: Any?, // Change the type accordingly if you know the type
    @SerializedName("telegram_channel_identifier")
    val telegramChannelIdentifier: String,
    @SerializedName("subreddit_url")
    val subredditUrl: String,
    @SerializedName("repos_url")
    val reposUrl: ReposUrl?
)

data class Image(
    val thumb: String,
    val small: String,
    val large: String
)

data class ReposUrl(
    val github: List<String>,
    val bitbucket: List<Any> // Change the type accordingly if you know the type
)

data class CommunityData(
    @SerializedName("facebook_likes")
    val facebookLikes: Any?, // Change the type accordingly if you know the type
    @SerializedName("twitter_followers")
    val twitterFollowers: Int,
    @SerializedName("reddit_average_posts_48h")
    val redditAveragePosts48h: Int,
    @SerializedName("reddit_average_comments_48h")
    val redditAverageComments48h: Int,
    @SerializedName("reddit_subscribers")
    val redditSubscribers: Int,
    @SerializedName("reddit_accounts_active_48h")
    val redditAccountsActive48h: Int,
    @SerializedName("telegram_channel_user_count")
    val telegramChannelUserCount: Any? // Change the type accordingly if you know the type
)

data class DeveloperData(
    val forks: Int,
    val stars: Int,
    val subscribers: Int,
    @SerializedName("total_issues")
    val totalIssues: Int,
    @SerializedName("closed_issues")
    val closedIssues: Int,
    @SerializedName("pull_requests_merged")
    val pullRequestsMerged: Int,
    @SerializedName("pull_request_contributors")
    val pullRequestContributors: Int,
    @SerializedName("code_additions_deletions_4_weeks")
    val codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks,
    @SerializedName("commit_count_4_weeks")
    val commitCount4Weeks: Int,
    @SerializedName("last_4_weeks_commit_activity_series")
    val last4WeeksCommitActivitySeries: List<Any> // Change the type accordingly if you know the type
)

data class CodeAdditionsDeletions4Weeks(
    val additions: Int,
    val deletions: Int
)


