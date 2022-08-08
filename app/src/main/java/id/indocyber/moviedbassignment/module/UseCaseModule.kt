package id.indocyber.moviedbassignment.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.indocyber.api_service.service.*
import id.indocyber.api_service.use_case.*

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetGenreListUseCase(genreListService: GenreListService) =
        GetGenreListUseCase(genreListService)

    @Provides
    fun provideGetMovieListUseCase(movieListService: MovieListService) =
        GetMovieListUseCase(movieListService)

    @Provides
    fun provideGetMovieDetailUseCase(movieDetailService: MovieDetailService) =
        GetMovieDetailUseCase(movieDetailService)

    @Provides
    fun provideGetMovieReviewUseCase(movieReviewService: MovieReviewService) =
        GetMovieReviewUseCase(movieReviewService)

    @Provides
    fun provideGetMovieVideoUseCase(movieVideoService: MovieVideoService) =
        GetMovieVideoUseCase(movieVideoService)
}