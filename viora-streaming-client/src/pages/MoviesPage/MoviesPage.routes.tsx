import type {RouteObject} from "react-router-dom";
import MoviesPage from "./MoviesPage.tsx";

export const MoviesPageChildRoutes: RouteObject[] = [
  {
    index: true,
    element: <MoviesPage />
  }
]