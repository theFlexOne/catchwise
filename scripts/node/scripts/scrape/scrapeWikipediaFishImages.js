import axios from "axios";
import fs from "fs";
import { JSDOM } from "jsdom";

const lakes = JSON.parse(fs.readFileSync("./data/newNewFormattedlakes.json"));
const fishSpeciesList = JSON.parse(fs.readFileSync("./data/fishSpecies.json"));

const wikipediaSearchUrl = "https://en.wikipedia.org/w/index.php";
const wikipediaFileUrl = "https://commons.wikimedia.org/wiki/File:";

async function scrapeWikipediaFishImages() {
  const requests = fishSpeciesList.map(scrapeWikipediaFishImage);
  return Promise.all(requests);
}

async function scrapeWikipediaFishImage(search) {
  const params = {
    search: search,
    title: "Special:Search",
    // profile: "advanced",
    // fulltext: "1",
    // advancedSearchCurrent: "%7B%7D",
    // ns0: "1",
  };
  const response = await axios.get(wikipediaSearchUrl, { params });
  if (!response.status === 200) console.log(search, "page unavailable");
  const dom = new JSDOM(response.data);
  const imageFileName = dom.window.document.querySelector("a.image > img")?.alt;
  if (!imageFileName) return console.log(search, "image not found");
  const url = new URL(wikipediaFileUrl + imageFileName);
  const filePage = new JSDOM((await axios.get(url.toString())).data);
  const imageUrl = filePage.window.document.querySelector(
    `a[title='${imageFileName}']`
  )?.href;

  console.log(imageUrl);

  return { search, imageFileName, imageUrl };
}

function gatherFishSpeciesList(lakes) {
  const fishSpecies = new Set();
  lakes.forEach((lake) => {
    lake.fish.forEach((fish) =>
      fishSpecies.add(
        fish.slice(0, 1).toUpperCase() + fish.slice(1).toLowerCase()
      )
    );
    lake.children.forEach((child) => {
      child.fish.forEach((fish) =>
        fishSpecies.add(
          fish.slice(0, 1).toUpperCase() + fish.slice(1).toLowerCase()
        )
      );
    });
  });
  return [...fishSpecies].filter((s) => s.split(" ").length === 2).sort();
}

async function go() {
  const images = await scrapeWikipediaFishImages();
  fs.writeFileSync("./data/fishImages2.json", JSON.stringify(images, null, 2));
}

go();
