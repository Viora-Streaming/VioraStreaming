import {useParams, useNavigate} from "react-router";
import {useMovie} from "../../hooks/useMovie.ts";
import {Box, Button, Chip, Stack, Typography} from "@mui/material";
import type {MovieDetails, Person} from "../../types/movieTypes.ts";
import AddIcon from "@mui/icons-material/Add";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import {MovieAssistantEnabler} from "./components/MovieAssistantEnabler.tsx";
import {API_PAGE} from "../../constants/routingConstants.ts";

export function MovieDetailsPage() {
  const {id} = useParams<{ id: string }>();
  const navigate = useNavigate();
  const {movie, isLoading} = useMovie(Number(id));

  if (isLoading) {
    return <div>Loading...</div>;
  }

  const handlePlay = () => {
    navigate(`/${API_PAGE.Movies}/${id}/player`);
  };

  return (
      <Box sx={{py: "50px", px: "48px"}}>
        <Stack spacing="64px" sx={{maxWidth: "1280px", margin: "0 auto"}}>
          <MovieDetails movie={movie} onPlay={handlePlay}/>
          <MovieCast actors={movie.actors}/>
        </Stack>
      </Box>
  );
}

type MovieDetailsProps = {
  movie: MovieDetails;
  onPlay: () => void;
};

function MovieDetails({movie, onPlay}: MovieDetailsProps) {
  return (
      <Stack
          spacing="48px"
          direction="row"
          sx={{alignItems: "center", justifyContent: "center"}}
      >
        <Box
            sx={{
              height: "360px",
              width: "240px",
              backgroundImage: `url(${movie.poster})`,
              backgroundSize: "cover",
              backgroundPosition: "center",
              overflow: "hidden",
              boxShadow: "0px 8px 24px rgba(0, 0, 0, 0.4)",
              borderRadius: "8px",
            }}
        />

        <Stack spacing="32px">
          <Stack spacing="16px" sx={{maxWidth: "650px"}}>
            <MovieLabels movie={movie}/>
            <Typography variant="h2" sx={{fontWeight: "bolder"}}>
              {movie.title}
            </Typography>
            <Typography variant="body1">{movie.plot}</Typography>
          </Stack>

          <MovieOperators movie={movie}/>

          <Stack spacing="16px" direction="row">
            {/* Play — navigates to the player route */}
            <Button
                variant="contained"
                onClick={onPlay}
                sx={{
                  textTransform: "none",
                  fontWeight: "bold",
                  p: "12px 32px",
                  borderRadius: "12px",
                }}
            >
              <PlayArrowIcon sx={{mr: 0.5}}/>
              Play
            </Button>

            <Button
                variant="outlined"
                sx={{
                  textTransform: "none",
                  fontWeight: "bold",
                  p: "12px 32px",
                  color: "text.primary",
                  borderRadius: "12px",
                }}
            >
              <AddIcon/>
              My List
            </Button>
          </Stack>
        </Stack>

        <MovieAssistantEnabler content="" onButtonClick={() => {
        }}/>
      </Stack>
  );
}

// ─── Sub-components (unchanged from your original) ───────────────────────────

type MovieCastProps = { actors: Person[] };

function MovieCast({actors}: MovieCastProps) {
  return (
      <Stack spacing="32px">
        <Typography variant="h5" sx={{fontWeight: "bold"}}>
          Featured Cast
        </Typography>
        <Stack spacing="24px" direction="row">
          {actors.map((actor) => (
              <ActorCard actor={actor}/>
          ))}
        </Stack>
      </Stack>
  );
}

function MovieLabels({movie}: { movie: MovieDetails }) {
  return (
      <Stack spacing="12px" direction="row">
        {[
          movie.genres[0].name,
          movie.releaseDate.split("-")[0],
          movie.rating.toFixed(1),
        ].map((label) => (
            <Chip
                key={label}
                size="small"
                label={label}
                sx={{
                  backgroundColor: "#6B7280",
                  color: "text.primary",
                  fontWeight: "bold",
                  borderRadius: "6px",
                  "& .MuiChip-label": {px: "8px", py: "4px"},
                }}
            />
        ))}
      </Stack>
  );
}

function MovieOperators({movie}: { movie: MovieDetails }) {
  const operators = [
    {label: "Director", value: movie.director.name},
    {label: "Writer", value: movie.writer.name},
    {label: "Rated", value: movie.rated},
    {label: "Duration", value: `${movie.durationInMinutes} Min.`},
  ];

  return (
      <Stack sx={{justifyContent: "space-between"}} direction="row">
        {operators.map((op) => (
            <Stack key={op.label} spacing="8px">
              <Typography variant="subtitle1" sx={{color: "text.secondary"}}>
                {op.label}
              </Typography>
              <Typography variant="body1" sx={{fontWeight: "bold"}}>
                {op.value}
              </Typography>
            </Stack>
        ))}
      </Stack>
  );
}

type ActorCardProps = { actor: Person };

function ActorCard({actor}: ActorCardProps) {
  return (
      <Stack spacing="16px" direction="column" alignItems="center">
        <Box
            sx={{
              height: "205px",
              width: "180px",
              backgroundImage: `url(${actor.photo})`,
              backgroundSize: "cover",
              backgroundPosition: "center",
              overflow: "hidden",
            }}
        />
        <Box>
          <Typography variant="body1" sx={{fontWeight: "bold"}}>
            {actor.name}
          </Typography>
        </Box>
      </Stack>
  );
}