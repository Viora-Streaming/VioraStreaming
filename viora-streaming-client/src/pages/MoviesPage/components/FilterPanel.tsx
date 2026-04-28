import { Box, Typography, Stack, Checkbox, FormControlLabel, Slider, Button } from "@mui/material";
import { VioraButton } from "../../../components/Button/VioraButton.tsx";
import * as React from 'react';
import {
  ANY_DURATION,
  DEFAULT_DURATION,
  DEFAULT_RATING,
  DEFAULT_RELEASE_YEAR, GENRES, LIST_OF_DURATION, MAX_DURATION
} from "../../../constants/filterConstants.ts";

export function FilterPanel() {
    const [selectedGenres, setSelectedGenres] = React.useState<string[]>([GENRES[1]]);
  const [rating, setRating] = React.useState<number>(DEFAULT_RATING);
  const [releaseYear, setReleaseYear] = React.useState<number[]>(DEFAULT_RELEASE_YEAR);
  const [duration, setDuration] = React.useState<string>(DEFAULT_DURATION);

  const handleGenreChange = (genre: string) => {
    setSelectedGenres((prev) =>
        prev.includes(genre) ? prev.filter((g) => g !== genre) : [...prev, genre]
    );
  };

  const handleResetFilters = () => {
    setSelectedGenres([]);
    setRating(DEFAULT_RATING);
    setReleaseYear(DEFAULT_RELEASE_YEAR);
    setDuration(ANY_DURATION);
  };

  return (
      <Box
          sx={{
            width: "280px",
            p: "32px 24px",
            backgroundColor: "background.paper",
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'space-between'
          }}
      >
        <Box>
          <Typography variant="h6" sx={{ fontWeight: 700, mb: "32px", fontSize: "20px" }}>
            Filters
          </Typography>

          {/* GENRES */}
          <Box>
            <Typography
                variant="body2"
                color="text.secondary"
                sx={{ textTransform: "uppercase", fontWeight: 700, mb: "16px", fontSize: "12px" }}>
              Genres
            </Typography>
            <Stack spacing="8px" sx={{ mb: "24px" }}>
              {GENRES.map((genre) => (
                  <FormControlLabel
                      key={genre}
                      control={
                        <Checkbox
                            checked={selectedGenres.includes(genre)}
                            onChange={() => handleGenreChange(genre)}
                            sx={{
                              color: 'text.secondary',
                              '&.Mui-checked': {
                                color: 'primary.main',
                              },
                            }}
                        />
                      }
                      label={
                        <Typography variant="body2" sx={{color: selectedGenres.includes(genre) ? 'text.primary' : 'text.secondary'}}>
                          {genre}
                        </Typography>
                      }
                  />
              ))}
            </Stack>
          </Box>
          <Button variant="text" color="primary" sx={{textTransform: 'none', mb: '24px'}}>Other</Button>


          {/* RATING */}
          <Stack direction="row"  sx={{ mb: "16px", justifyContent: "space-between" }}>
            <Typography variant="body2" color="text.secondary" sx={{ textTransform: "uppercase", fontWeight: 700 }}>
              Rating
            </Typography>
            <Typography variant="body2" color="primary.main" sx={{ fontWeight: 700 }}>
              {rating.toFixed(1)}+
            </Typography>
          </Stack>
          <Slider
              value={rating}
              onChange={(_, newValue) => setRating(newValue as number)}
              min={0}
              max={10}
              step={0.1}
              valueLabelDisplay="auto"
              sx={{
                mb: "24px",
                color: 'primary.main',
                '& .MuiSlider-thumb': {
                  backgroundColor: 'primary.main',
                },
                '& .MuiSlider-track': {
                  backgroundColor: 'primary.main',
                },
                '& .MuiSlider-rail': {
                  backgroundColor: 'secondary.main',
                },
              }}
          />

          {/* RELEASE YEAR */}
          <Stack direction="row" sx={{ mb: "16px" }}>
            <Typography variant="body2" color="text.secondary" sx={{ textTransform: "uppercase", fontWeight: 700 }}>
              Release Year
            </Typography>
            <Typography variant="body2" color="primary.main" sx={{ fontWeight: 700, textAlign: 'right', paddingLeft: "50px" }}>
              {releaseYear[0]} -<br/> {releaseYear[1]}
            </Typography>
          </Stack>
          <Slider
              value={releaseYear}
              onChange={(_, newValue) => setReleaseYear(newValue as number[])}
              min={1990}
              max={new Date().getFullYear()}
              step={1}
              valueLabelDisplay="auto"
              sx={{
                mb: "24px",
                color: 'primary.main',
              }}
          />

          <Typography variant="body2" color="text.secondary" sx={{ textTransform: "uppercase", fontWeight: 700, mb: "16px" }}>
            Duration
          </Typography>
          <Stack direction="row" spacing="8px" sx={{mb: "32px"}}>
            {LIST_OF_DURATION.map((option) => (
                <Button
                    key={option}
                    variant={duration === option ? "contained" : "outlined"}
                    onClick={() => setDuration(option)}
                    sx={{
                      flex: 1,
                      textTransform: "none",
                      borderRadius: '12px',
                      borderColor: duration === option ? 'primary.main' : 'secondary.main',
                      backgroundColor: duration === option ? 'primary.main' : 'secondary.main',
                      color: duration === option ? 'text.primary' : 'text.secondary',
                      '&:hover': {
                        backgroundColor: duration === option ? 'primary.main' : 'secondary.main',
                        borderColor: duration === option ? 'primary.main' : 'secondary.main',
                        opacity: 0.9
                      }
                    }}
                >
                  <Typography variant="caption" sx={{fontWeight: 'bold'}}>
                    {option === MAX_DURATION ? `${MAX_DURATION} min` : option === DEFAULT_DURATION ? `${DEFAULT_DURATION} min` : option}
                  </Typography>
                </Button>
            ))}
          </Stack>
          <VioraButton name="Reset all filters" onClick={handleResetFilters} />
        </Box>
      </Box>
  );
}