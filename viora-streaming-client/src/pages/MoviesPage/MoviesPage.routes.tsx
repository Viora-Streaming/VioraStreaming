import type {RouteObject} from "react-router-dom";
import MoviesPage from "./MoviesPage.tsx";
import {API_PATHS} from "../../constants/apiConstants.ts";
import {MovieDetailsPage} from "../MovieDetailsPage/MovieDetailsPage.tsx";
import {API_PAGE, PAGE_ROUTES} from "../../constants/routingConstants.ts";

export const MoviesPageChildRoutes: RouteObject[] = [
  {
    index: true,
    element: <MoviesPage />
  },
  {
    path: `${API_PAGE.Movies}/:id`,
    element: <MovieDetailsPage />
  }
]