import fs from "fs";
import axios from "axios";
import { JSDOM } from "jsdom";

function formatLakeFishSpecies() {
  const lakeData = JSON.parse(fs.readFileSync("./data/newNewNewLakeData.json"));

  const newNewNewLakeData = lakeData.map((lake) => {
    const fishSpecies = lake.fishSpecies.map((fish) => {
      return fish.species;
    });
    return {
      ...lake,
      fishSpecies,
    };
  });

  fs.writeFileSync(
    "./data/newNewNewLakeData.json",
    JSON.stringify(newNewNewLakeData, null, 2)
  );
}

formatLakeFishSpecies();

function createLakeFishIdsList() {
  const lakeData = JSON.parse(fs.readFileSync("./data/newLakeData.json"));
  const fishData = JSON.parse(fs.readFileSync("./data/newNewFishData.json"));

  const lakeFishIdsList = lakeData.map((lake) => {
    const fishIds = lake.fishSpecies.map((fish) => {
      const endIdx = fish.indexOf("(");
      if (endIdx > -1) {
        fish = fish.slice(0, endIdx - 1);
      }

      const fishDataFish = fishData.find((fishDataFish) => {
        return fishDataFish.commonNames.includes(fish);
      });
      return { name: fish, id: fishDataFish?.species || "" };
    });
    return {
      ...lake,
      fishSpecies: fishIds,
    };
  });

  fs.writeFileSync(
    "./data/newNewLakeData.json",
    JSON.stringify(lakeFishIdsList, null, 2)
  );
}

function addCommonNamesToNewFishData() {
  const commonNamesList = JSON.parse(
    fs.readFileSync("./data/fishBaseCommonNames.json")
  );
  const newFishData = JSON.parse(fs.readFileSync("./data/newFishData.json"));

  const newFishDataWithCommonNames = newFishData.map((fish) => {
    const commonNames = new Set(
      commonNamesList[fish.species]?.map((name) => name.toLowerCase()) || []
    );
    commonNames.add(fish.name);
    return {
      ...fish,
      commonNames: [...commonNames].sort(),
    };
  });
  fs.writeFileSync(
    "./data/newNewFishData.json",
    JSON.stringify(newFishDataWithCommonNames, null, 2)
  );
}

async function scrapeAllCommonNamesFromFishBase() {
  const baseUrl = "https://www.fishbase.se/ComNames/CommonNamesList.php";
  const fishBaseFishIds = JSON.parse(
    fs.readFileSync("./data/fishBaseFishIds.json")
  );

  const fetches = fishBaseFishIds.map((fish) => {
    const url = new URL(baseUrl);
    url.searchParams.append("ID", fish.id);
    return axios.get(url);
  });

  const responseList = await Promise.all(fetches);
  const commonNames = responseList.reduce((acc, res, i) => {
    const { data } = res;
    const fishName = fishBaseFishIds[i].name;
    const names = scrapeCommonNamesFromHTML(data);
    acc[fishName] = names;
    return acc;
  }, {});

  fs.writeFileSync(
    "./data/fishBaseCommonNames.json",
    JSON.stringify(commonNames, null, 2)
  );
}

function scrapeCommonNamesFromHTML(html) {
  const { document } = new JSDOM(html).window;
  const table = document.querySelector("#dataTable");
  const rows = [...table.querySelectorAll("tr")];
  const commonNames = rows.reduce((names, row) => {
    if (row.children[1].textContent.trim() === "USA")
      names.push(row.children[0].textContent.trim());
    return names;
  }, []);
  return commonNames;
}

function getAllFishSpeciesInLake() {
  const lakeData = JSON.parse(fs.readFileSync("./data/newLakeData.json"));
  const allFishSpeciesInLakeData = lakeData.reduce((acc, lake) => {
    lake.fishSpecies.forEach((fish) => {
      acc.add(fish);
    });
    return acc;
  }, new Set());
  return allFishSpeciesInLakeData;
}
async function getFishBaseFishIds() {
  const fishData = JSON.parse(fs.readFileSync("./data/newFishData.json"));
  const fishSpecies = fishData.map((fish) => fish.species);

  const idFetches = fishSpecies.reduce(
    (acc, species) => {
      if (species.includes("/")) return acc;
      const url = new URL("https://www.fishbase.org.au/v4/search");
      url.searchParams.append("q", species);

      acc[0].push(axios.get(url));
      acc[1].push(species);
      return acc;
    },
    [[], []]
  );

  const responseList = await Promise.all(idFetches[0]);
  const idMap = responseList.reduce((acc, res, i) => {
    const {
      request: { path },
    } = res;
    const id = path.split("/").at(-1);
    const name = idFetches[1][i];
    acc.push({ name, id });
    return acc;
  }, []);
  fs.writeFileSync(
    "./data/fishBaseFishIds.json",
    JSON.stringify(idMap, null, 2)
  );
}

function createNewLakeData() {
  const newLakeData = lakeData.map((lake) => {
    return {
      localId: lake?.id || "",
      name: lake?.name.toLowerCase() || "",
      county: lake?.county.toLowerCase() || "",
      countyId: lake?.county_id || "",
      state: "MN",
      nearestTown: lake?.nearest_town.toLowerCase() || "",
      coords: {
        lat: lake?.point["epsg:4326"][0] || 0,
        lng: lake?.point["epsg:4326"][1] || 0,
      },
      fishSpecies: lake?.fishSpecies || [],
    };
  });

  fs.writeFileSync(
    "./data/newLakeData.json",
    JSON.stringify(newLakeData, null, 2)
  );
}

function createNewFishData() {
  const fishData = JSON.parse(fs.readFileSync("./data/fishData.json"));
  const fishBaseData = JSON.parse(
    fs.readFileSync("./data/fishBaseFishList.json")
  );
  const newFishData = fishData.map((fish, i) => {
    const fishName = fish.name.toLowerCase();
    const fishBase = fishBaseData.find((fishBase) => {
      const commonNames = fishBase.commonNames.split(",").map((name) => {
        const idx = name.indexOf("(");
        if (idx > -1) {
          name = name.slice(0, idx);
        }
        return name.trim().toLowerCase();
      });
      if (commonNames.includes(fishName)) {
        return fishBase;
      }
    });
    !fishBase && console.log(fishName);
    return {
      name: fish.name.toLowerCase(),
      family: fishBase?.family.trim().toLowerCase() || "",
      species: fishBase?.species.trim().toLowerCase() || "",
      description: fish.description,
      identification: fish.identification,
    };
  });
  fs.writeFileSync("./data/newFishData.json", JSON.stringify(newFishData));
}
