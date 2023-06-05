import fs from 'fs';

const data = JSON.parse(fs.readFileSync('./data/statesWithCountiesGeoData.json', 'utf8'));

data.forEach((state: any) => {
  fs.writeFileSync(`./data/states/${state.stusps}_geo_data.json`.toLowerCase(), JSON.stringify(state));
});