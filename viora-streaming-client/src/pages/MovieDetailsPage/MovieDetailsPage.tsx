import {useParams} from "react-router";
import {useMovie} from "../../hooks/useMovie.ts";
import {Box, Button, Chip, Stack, Typography} from "@mui/material";
import type {MovieDetails, Person} from "../../types/movieTypes.ts";
import AddIcon from '@mui/icons-material/Add';
import {MovieAssistantEnabler} from "./components/MovieAssistantEnabler.tsx";

export function MovieDetailsPage() {
  const {id} = useParams<{ id: number }>();
  const {movie, isLoading} = useMovie(id);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
      <Box sx={{
        py: "50px",
        px: "48px",
      }}>

        <Stack spacing="64px" sx={{
          maxWidth: "1280px",
          margin: "0 auto",
        }}>
          <MovieDetails movie={movie}/>

          <MovieCast actors={movie.actors}/>
        </Stack>
      </Box>
  )
}

type MovieDetailsProps = {
  movie: MovieDetails
}

function MovieDetails({movie}: MovieDetailsProps) {
  return (
      <Stack
          spacing="48px"
          direction="row"
          sx={{
            alignItems: "center",
            justifyContent: "center",
          }}
      >
        <Box
            sx={{
              height: "360px",
              width: "240px",
              backgroundImage: `url(${movie.poster})`,
              backgroundSize: "cover",
              backgroundPosition: "center",
              overflow: 'hidden',
              boxShadow: "0px 8px 24px rgba(0, 0, 0, 0.4)",
              borderRadius: "8px",
            }}
        />

        <Stack spacing="32px">
          <Stack spacing="16px" sx={{maxWidth: "650px"}}>
            <MovieLabels movie={movie}/>
            <Typography variant="h2"
                        sx={{fontWeight: "bolder"}}>{movie.title}</Typography>
            <Typography variant="body1">{movie.plot}</Typography>
          </Stack>

          <MovieOperators movie={movie}/>

          <Stack spacing="16px" direction="row">
            <Button variant='contained'
                    sx={{
                      textTransform: "none",
                      fontWeight: "bold",
                      p: "12px 32px",
                      borderRadius: "12px",
                    }}>
              <AddIcon/>
              Play
            </Button>
            <Button variant='outlined'
                    sx={{
                      textTransform: "none",
                      fontWeight: "bold",
                      p: "12px 32px",
                      color: "text.primary",
                      borderRadius: "12px",
                    }}>
              <AddIcon/>
              My List
            </Button>
          </Stack>

        </Stack>
        <MovieAssistantEnabler content="" onButtonClick={() => {
        }}/>
      </Stack>
  )
}

type MovieCastProps = {
  actors: Person[]
}

function MovieCast({actors}: MovieCastProps) {
  return (
      <Stack spacing="32px">
        <Typography variant="h5" sx={{fontWeight: "bold"}}>Featured Cast</Typography>

        <Stack spacing="24px" direction="row">
          {actors.map((actor) => (
              <ActorCard actor={actor}/>
          ))}
        </Stack>

      </Stack>
  )
}

function MovieLabels({movie}: MovieDetailsProps) {
  return (
      <Stack spacing="12px" direction="row">
        <Chip
            size="small"
            label={movie.genres[0].name}
            sx={{
              backgroundColor: "#6B7280",
              color: "text.primary",
              fontWeight: "bold",
              borderRadius: '6px',
              '& .MuiChip-label': {px: '8px', py: '4px'}
            }}
        />
        <Chip
            size="small"
            label={movie.releaseDate.split("-")[0]}
            sx={{
              backgroundColor: "#6B7280",
              color: "text.primary",
              fontWeight: "bold",
              borderRadius: '6px',
              '& .MuiChip-label': {px: '8px', py: '4px'}
            }}
        />
        <Chip
            size="small"
            label={movie.rating.toFixed(1)}
            sx={{
              backgroundColor: "#6B7280",
              color: "text.primary",
              fontWeight: "bold",
              borderRadius: '6px',
              '& .MuiChip-label': {px: '8px', py: '4px'}
            }}
        />
      </Stack>
  )
}

function MovieOperators({movie}: MovieDetailsProps) {
  const operators = [
    {
      label: "Director",
      value: movie.director.name
    },
    {
      label: "Writer",
      value: movie.writer.name
    },
    {
      label: "Rated",
      value: movie.rated
    },
    {
      label: "Duration",
      value: `${movie.durationInMinutes} Min.`
    }
  ]

  return (
      <Stack sx={{justifyContent: "space-between"}} direction="row">
        {operators.map((operator) => (
            <Stack spacing="8px">
              <Typography variant="subtitle1" sx={{
                color: "text.secondary"
              }}>{operator.label}</Typography>
              <Typography variant="body1" sx={{fontWeight: "bold"}}>{operator.value}</Typography>
            </Stack>
        ))}
      </Stack>
  )
}

type ActorCardProps = {
  actor: Person
}

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
              overflow: 'hidden'
            }}
        />

        <Box>
          <Typography variant="body1" sx={{fontWeight: "bold"}}>{actor.name}</Typography>
        </Box>
      </Stack>
  )
}
