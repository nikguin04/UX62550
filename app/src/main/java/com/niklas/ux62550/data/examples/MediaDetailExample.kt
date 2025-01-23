package com.niklas.ux62550.data.examples

import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.TrailerLink
import com.niklas.ux62550.data.model.TrailerObject

class MediaDetailExample {
    companion object {
        val MediaDetailObjectExample: MovieDetailObject =
            MovieDetailObject(
                spokenLanguages = listOf(),
                genre = listOf(),
                adult = false,
                backDropPath = "/vZG7PrX9HmdgL5qfZRjhJsFYEIA.jpg",
                homePage = "https://venom.movie",
                Originaltitle = "Venom: The Last Dance",
                Description = "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.",
                posterPath = "/670x9sf0Ru8y6ezBggmYudx61yB.jpg",
                releaseDate = "2024-10-22",
                title = "Venom: The Last Dance",
                vote_average = 6.8,
                runTime = 109,
                id = 912649
            )

        val TrailerObjectExample: TrailerObject =
            TrailerObject(
                movieTrailerID = 0,
                resultsTrailerLinks = listOf(
                    TrailerLink(type = "idk", key = "some link maybe")
                )
            )
    }
}
