package task.localisation

import task.localisation.entity.LanguageKey

const val TASK_GROUP = "task/localisation"
const val DEFAULT_LANGUAGE = "en"

internal val languageKeyLists = listOf(
    LanguageKey(google = "en", android = "en"), // English - Worldwide
    LanguageKey(google = "ar", android = "ar"), // Arabic - Middle East, North Africa
    LanguageKey(google = "bg", android = "bg"), // Bulgarian - Bulgaria
    LanguageKey(google = "da", android = "da"), // Danish - Denmark
    LanguageKey(google = "de", android = "de"), // German - Germany, Austria, Switzerland
    LanguageKey(google = "el", android = "el"), // Greek - Greece
    LanguageKey(google = "es", android = "es"), // Spanish - Spain, Latin America
    LanguageKey(google = "fa", android = "fa"), // Persian - Iran
    LanguageKey(google = "fi", android = "fi"), // Finnish - Finland
    LanguageKey(google = "fr", android = "fr"), // French - France, Canada, Africa
    LanguageKey(google = "hi", android = "hi"), // Hindi - India
    LanguageKey(google = "id", android = "in"), // Indonesian - Indonesia
    LanguageKey(google = "it", android = "it"), // Italian - Italy
    LanguageKey(google = "ja", android = "ja"), // Japanese - Japan
    LanguageKey(google = "ko", android = "ko"), // Korean - South Korea
    LanguageKey(google = "lt", android = "lt"), // Lithuanian - Lithuania
    LanguageKey(google = "ms", android = "ms"), // Malay - Malaysia, Indonesia
    LanguageKey(google = "nl", android = "nl"), // Dutch - Netherlands, Belgium
    LanguageKey(google = "no", android = "no"), // Norwegian - Norway
    LanguageKey(google = "pl", android = "pl"), // Polish - Poland
    LanguageKey(google = "pt", android = "pt"), // Portuguese - Brazil, Portugal
    LanguageKey(google = "ro", android = "ro"), // Romanian - Romania
    LanguageKey(google = "ru", android = "ru"), // Russian - Russia, Eastern Europe
    LanguageKey(google = "sr", android = "sr"), // Serbian - Serbia
    LanguageKey(google = "sv", android = "sv"), // Swedish - Sweden
    LanguageKey(google = "sw", android = "sw"), // Swahili - East Africa
    LanguageKey(google = "th", android = "th"), // Thai - Thailand
    LanguageKey(google = "tr", android = "tr"), // Turkish - Turkey
    LanguageKey(google = "uk", android = "uk"), // Ukrainian - Ukraine
    LanguageKey(google = "vi", android = "vi"), // Vietnamese - Vietnam
    LanguageKey(google = "zh-CN", android = "zh"), // Chinese (Simplified) - China
    LanguageKey(google = "zh-TW", android = "zh-rTW"), // Chinese (Traditional) - Taiwan
    LanguageKey(google = "ca", android = "ca"), // Catalan
    LanguageKey(google = "cs", android = "cs"), // Czech
    LanguageKey(google = "cy", android = "cy"), // Welsh
    LanguageKey(google = "et", android = "et"), // Estonian
    LanguageKey(google = "eu", android = "eu"), // Basque
    LanguageKey(google = "ga", android = "ga"), // Irish
    LanguageKey(google = "gl", android = "gl"), // Galician
    LanguageKey(google = "he", android = "iw"), // Hebrew
    LanguageKey(google = "hr", android = "hr"), // Croatian
    LanguageKey(google = "hu", android = "hu"), // Hungarian
    LanguageKey(google = "is", android = "is"), // Icelandic
    LanguageKey(google = "ka", android = "ka"), // Georgian
    LanguageKey(google = "kk", android = "kk"), // Kazakh
    LanguageKey(google = "ky", android = "ky"), // Kyrgyz
    LanguageKey(google = "lb", android = "lb"), // Luxembourgish
    LanguageKey(google = "mk", android = "mk"), // Macedonian
    LanguageKey(google = "mn", android = "mn"), // Mongolian
    LanguageKey(google = "mt", android = "mt"), // Maltese
    LanguageKey(google = "my", android = "my"), // Burmese
    LanguageKey(google = "ne", android = "ne"), // Nepali
    LanguageKey(google = "sk", android = "sk"), // Slovak
    LanguageKey(google = "sl", android = "sl"), // Slovenian
    LanguageKey(google = "sq", android = "sq"), // Albanian
    LanguageKey(google = "ta", android = "ta"), // Tamil
    LanguageKey(google = "te", android = "te"), // Telugu
    LanguageKey(google = "ur", android = "ur"), // Urdu
    LanguageKey(google = "lv", android = "lv"), // Latvian
    LanguageKey(google = "nb", android = "nb"), // Norway
    LanguageKey(google = "tl", android = "tl"), // Tagalog
)

internal fun convertGoogleToAndroid(language: String): String =
    languageKeyLists.find { it.google == language }!!.android

internal fun String.removeHtmlTags(): String {
    return this.replace(removeHtmlTag, "")
}

internal fun String.isContainHtmlTags(): Boolean {
    return matches(htmlTag)
}

internal fun String.isSingleWord(): Boolean {
    return trim().matches(Regex("^\\w+$"))
}

internal fun String.replaceApostrophe(): String {
    return replace("'", "\u2019")
}

private val removeHtmlTag = "<[^>]*>".toRegex()
private val htmlTag = "<[^>]+>".toRegex()