import fs from "fs";
import capitalize from "./helpers/capitalize";
import { Lake, LakeComponent, LakeData } from "./index.d";

async function main() {
  const lakeData: LakeData[] = JSON.parse(
    fs.readFileSync("./data/lakesFromServer.json", "utf8")
  );

  const lakes = addComponentsToLakes(...categorizeRawLakeData(lakeData));

  const fuckUps = lakes.filter((lake) => lake.localId.at(-1) != "0");
  console.log(fuckUps);

  fs.writeFileSync(
    `./data/lakesWithComponents.json`,
    JSON.stringify(lakes, null, 2)
  );

  console.log("Done!");

  function categorizeRawLakeData(data: LakeData[]): [Lake[], LakeComponent[]] {
    const lakes = new Array<Lake>();
    const lakeComponents = new Array<LakeComponent>();
    for (const ld of data) {
      if (ld.localId.at(-1) === "0") {
        lakes.push({
          localId: ld.localId,
          name: ld.name,
          countyId: ld.countyId,
          nearestTown: ld.nearestTown,
          coordinates: ld.coordinates,
          fish: ld.fish,
          components: [],
        });
      } else {
        lakeComponents.push({
          localId: ld.localId,
          name: ld.name,
          coordinates: ld.coordinates,
          fish: ld.fish,
        });
      }
    }
    return [lakes, lakeComponents];
  }

  function addComponentsToLakes(
    lakes: Lake[],
    lakeComponents: LakeComponent[]
  ): Lake[] {
    for (const lake of lakes) {
      const components = lakeComponents.filter((lc) =>
        lc.localId.startsWith(lake.localId.slice(0, -1))
      );
      lake.components = components;
    }
    return lakes;
  }
}

main();
