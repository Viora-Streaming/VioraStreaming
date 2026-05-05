import {Stack, Typography, Link, Box} from "@mui/material";
import {useTrendingMovies} from "../../hooks/useMovies.ts";
import {useHistory} from "../../hooks/useHistory.ts";
import {IntroMovie} from "./components/IntroMovie.tsx";
import type {MovieSummary} from "../../types/movieTypes.ts";
import {TrendingMovieCard} from "./components/TrendingMovieCard.tsx";
import {HistoryMovieCard} from "./components/HistoryMovieCard.tsx";
import type {History} from "../../types/historyTypes.ts";
import {NavLink} from "react-router-dom";
import {API_PAGE} from "../../constants/routingConstants.ts";
import {WeekendSpecial} from "./components/WeekendSpecial.tsx";
import {SmartRecommender} from "./components/SmartRecommender.tsx";
import {JoinTheCommunity} from "./components/JoinTheCommunity.tsx";

export default function HomePage() {

  const {movies, isLoading} = useTrendingMovies();
  const {histories, isLoading: historyLoading} = useHistory();

  if (isLoading || historyLoading) {
    return <div>Loading...</div>;
  }

  return (
      <Stack spacing="48px">
        <IntroMovie movie={movies[0]}/>
        <Stack spacing="64px" sx={{p: "64px"}}>
          <ContinueWatchingSection histories={histories}/>
          <TrendingNowSection movies={movies}/>
          <CuratedForYou movie={movies[1]}/>
        </Stack>
      </Stack>
  );
}

type ContinueWatchingProps = {
  histories: History[];
}

function ContinueWatchingSection({histories}: ContinueWatchingProps) {
  return (
      <Stack spacing="24px">
        <Stack direction="row" sx={{
          justifyContent: "space-between",
          alignItems: "center"
        }}>
          <Typography variant="h5" sx={{fontWeight: "bold"}}>Continue Watching</Typography>
          <Link component={NavLink} to={API_PAGE.History} underline="none">
            View All
          </Link>
        </Stack>
        <Stack spacing="24px" direction="row">
          {histories.slice(0, 6).map((history) => (
              <HistoryMovieCard history={history} onClick={() => {
              }}/>
          ))}
        </Stack>
      </Stack>
  )
}

type TrendingNowProps = {
  movies: MovieSummary[];
}

function TrendingNowSection({movies}: TrendingNowProps) {
  return (
      <Stack spacing="24px">
        <Typography variant="h5" sx={{fontWeight: "bold"}}>Trending Now</Typography>
        <Stack spacing="24px" direction="row">
          {movies.slice(0, 6).map((movie) => (
              <TrendingMovieCard movie={movie} onClick={() => {
              }}/>
          ))}
        </Stack>
      </Stack>
  )
}

type CuratedForYouProps = {
  movie: MovieSummary;
}

function CuratedForYou({movie}: CuratedForYouProps) {
  return (
      <Box sx={{
        display: "flex",
        flexDirection: "column",
        gap: "24px",
      }}>
        <Typography variant="h6" sx={{fontWeight: "bold"}}>Curated For You</Typography>
        <Stack direction="row" spacing="24px">
          <WeekendSpecial movie={movie}/>
          <SmartRecommender/>
          <JoinTheCommunity />
        </Stack>
      </Box>
  )
}

