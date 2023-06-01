export type Coordinate = {
  longitude: number;
  latitude: number;
};

export type Fish = {
  name: string;
  species: string;
};

export type Lake = {
  localId: string;
  name: string;
  countyId: number;
  nearestTown: string;
  coordinates: Coordinate;
  fish: Fish[];
  components: LakeComponent[];
};

export type LakeComponent = {
  localId: string;
  name: string;
  coordinates: Coordinate;
  fish: Fish[];
};

export type LakeData = {
  localId: string;
  name: string;
  countyId: number;
  nearestTown: string;
  coordinates: Coordinate;
  fish: Fish[];
};
