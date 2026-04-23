import type {RouteObject} from "react-router-dom";
import WelcomePage from "./WelcomePage.tsx";
import {API_PAGE, PAGE_ROUTES} from "../../constants/routingConstants.ts";
import {RegisterPage} from "./subpages/RegisterPage.tsx";

export const WelcomePageChildRoutes: RouteObject[] = [
  {
    index: true,
    element: <WelcomePage />
  },
  {
    path: API_PAGE.Auth,
    children: [
      {
        path: PAGE_ROUTES.Register,
        element: <RegisterPage/>
      }
    ]
  }
]
