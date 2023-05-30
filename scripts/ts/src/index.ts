import fs from "fs";
import { Lake } from "./index.d";
import capitalize from "./helpers/capitalize";

interface LakeWithChildren extends Lake {
  children: Lake[];
}

async function main(iteration: number = 1) {
  const lakeData: Lake[] = JSON.parse(
    fs.readFileSync("./data/rawLakesData.json", "utf8")
  );

  const sortedLakesData = lakeData.reduce(
    (acc, lake) => {
      lake.name = lake.name == null ? "" : capitalize(lake.name);

      const newLakeFish = lake.fish.map(({ name, species }) => ({
        name,
        species:
          species == null
            ? ""
            : `${capitalize(species).split(" ")[0]} ${species
                .split(" ")
                .slice(1)
                .join(" ")}`,
      }));
      lake.fish = newLakeFish;

      const isParent = lake.localId.at(-1) === "0";
      acc[isParent ? "lakes" : "other"].push(lake);
      return acc;
    },
    { lakes: [], other: [] } as { lakes: Lake[]; other: Lake[] }
  );

  const newLakesData = sortedLakesData.other.reduce(
    (acc, lake) => {
      const parentId = lake.localId.slice(0, -1) + "0";
      const parent = sortedLakesData.lakes.find(
        (parent) => parent.localId === parentId
      ) as LakeWithChildren;
      if (parent == null) {
        const newLake = lake as LakeWithChildren;
        newLake.children = [];
        acc.push(lake as LakeWithChildren);
      } else {
        parent.children ||= [] as Lake[];
        parent.children.push(lake);
      }
      return acc;
    },
    [...sortedLakesData.lakes] as LakeWithChildren[]
  );

  fs.writeFileSync(
    `./data/lakesData_${iteration}.json`,
    JSON.stringify(newLakesData, null, 2)
  );
}

main();
