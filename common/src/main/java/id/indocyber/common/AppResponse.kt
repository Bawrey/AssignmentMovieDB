package id.indocyber.common

open class AppResponse<T> {
    companion object {
        fun <T> success(data: T): AppResponse<T> =
            AppResponseSuccess(data)

        fun <T> error(e: Throwable?): AppResponse<T> =
            AppResponseError(e)

        fun <T> loading() = AppResponseLoading<T>()
    }
}

class AppResponseSuccess<T>(val data: T) : AppResponse<T>()
class AppResponseError<T>(val e: Throwable?) : AppResponse<T>()
class AppResponseLoading<T> : AppResponse<T>()