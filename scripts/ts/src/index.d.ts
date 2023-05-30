export interface Coordinate {
  longitude: number;
  latitude: number;
}

export interface Fish {
  name: string;
  species: string;
}

export interface Lake {
  localId: string;
  name: string;
  countyId: number;
  nearestTown: string;
  coordinates: Coordinate;
  fish: Fish[];
}
