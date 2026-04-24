import type {RouteObject} from "react-router-dom";
import HomePage from "./HomePage.tsx";

export const HomePageChildRoutes: RouteObject[] = [
  {
    index: true,
    element: <HomePage />
  }
]
