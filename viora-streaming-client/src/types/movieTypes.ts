export interface Genre {
  id: number;
  name: string;
}

export interface MovieSummary {
  id: number;
  title: string;
  poster: string;
  releaseDate: string;
  genres: string[];
  rating: number;
}

export interface Duration {
  from?: number;
  to?: number;
}

export interface Pageable {
  page: number;
  size: number;
}

export interface MovieFilter {
  search?: string;
  genres: number[];
  rating: number;
  releaseYear: ReleaseYear;
  duration: Duration;
  page: number;
  size: number;
}

export interface ReleaseYear {
  from: number;
  to: number;
}