import type {RouteObject} from "react-router-dom";
import MoviesPage from "./MoviesPage.tsx";
import {MovieDetailsPage} from "../MovieDetailsPage/MovieDetailsPage.tsx";
import {API_PAGE} from "../../constants/routingConstants.ts";
import {MoviePlayer} from "../MoviePlayer/MoviePlayer.tsx";

export const MoviesPageChildRoutes: RouteObject[] = [
  {
    index: true,
    element: <MoviesPage />
  },
  {
    path: `${API_PAGE.Movies}/:id`,
    element: <MovieDetailsPage />
  },
  {
    path: `${API_PAGE.Movies}/:id/player`,
    element: <MoviePlayer />
  }
]