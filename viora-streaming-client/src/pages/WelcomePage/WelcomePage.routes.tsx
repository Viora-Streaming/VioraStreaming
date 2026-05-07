import type {RouteObject} from "react-router-dom";
import WelcomePage from "./WelcomePage.tsx";
import {API_PAGE, PAGE_ROUTES} from "../../constants/routingConstants.ts";
import {RegisterPage} from "./subpages/RegisterPage.tsx";
import {ForgotPassword} from "./subpages/ForgotPassword.tsx";
import {DropPasswordPage} from "./subpages/DropPasswordPage.tsx";

export const WelcomePageChildRoutes: RouteObject[] = [
  {
    index: true,
    element: <WelcomePage/>
  },
  {
    path: API_PAGE.Auth,
    children: [
      {
        path: PAGE_ROUTES.Register,
        element: <RegisterPage/>
      },
      {
        path: PAGE_ROUTES.ForgotPassword,
        element: <ForgotPassword/>
      },
      {
        path: PAGE_ROUTES.DropPassword,
        element: <DropPasswordPage/>
      }
    ]
  }
]
