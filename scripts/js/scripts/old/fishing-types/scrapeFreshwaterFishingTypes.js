import axios from "axios";
import { JSDOM } from "jsdom";
import fs from "fs";

const URL_ORIGIN = "https://www.takemefishing.org";
const URL_PATHNAME = "freshwater-fishing/types-of-freshwater-fishing";

const LINK_PATHS = [
  "lakes-and-ponds",
  "river-fishing",
  "lake-fishing",
  "pond-fishing",
  "reservoirs-and-flowages",
];

export default async function scrapeTypesOfFreshwaterFishing() {
  const content = await Promise.all(
    LINK_PATHS.map((path) => {
      const url = new URL(URL_PATHNAME + "/" + path, URL_ORIGIN);
      return scrapeFreshwaterFishingType(url.toString());
    })
  );

  fs.writeFileSync(
    "./data/freshwaterFishingTypes.json",
    JSON.stringify(content)
  );
}

async function scrapeFreshwaterFishingType(url) {
  try {
    const { data } = await axios.get(url);
    return buildFreshwaterFishingType(data);
  } catch (err) {
    console.error(err);
  }
}

function buildFreshwaterFishingType(html) {
  const document = new JSDOM(html).window.document;

  const fishingTypeObject = {
    heading: document.querySelector("h1").textContent,
  };

  const mainContent =
    document.querySelector(".detail-content") ||
    document.querySelector(".content-items");

  const sectionHeadingIndices = [];

  [...mainContent.children].forEach(
    (child, i) => child.tagName === "H2" && sectionHeadingIndices.push(i)
  );

  fishingTypeObject.text = [...mainContent.children]
    .slice(0, sectionHeadingIndices[0])
    .map((child) => child.textContent)
    .join("\n");

  fishingTypeObject.sections = sectionHeadingIndices.reduce(
    (acc, cur, i, arr) => {
      const heading = mainContent.children[cur].textContent;
      const endIdx =
        i + 1 === arr.length ? mainContent.children.length : arr[i + 1];
      const subSectionIndices = [];
      [...mainContent.children].forEach((child, i) => {
        if (i > cur && i < endIdx && child.tagName === "H3") {
          subSectionIndices.push(i);
        }
      });

      const textContent = [...mainContent.children]
        .slice(cur + 1, subSectionIndices[0])
        .map((child) => child.textContent)
        .join("\n");
      const sections = subSectionIndices.reduce((acc, cur, i, arr) => {
        const heading = mainContent.children[cur].textContent;
        const endIdx =
          i + 1 === arr.length ? mainContent.children.length : arr[i + 1];
        const textContent = [...mainContent.children]
          .slice(cur + 1, endIdx)
          .map((child) => child.textContent)
          .join("\n");
        acc.push({ heading, textContent });
        return acc;
      }, []);
      acc.push({ heading, textContent, sections });
      return acc;
    },
    []
  );
  return fishingTypeObject;
}
