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
  const url = new URL(URL_PATHNAME + "/" + LINK_PATHS[0], URL_ORIGIN);
  scrapeFreshwaterFishingType(url.toString());
}

async function scrapeFreshwaterFishingType(url) {
  try {
    const { data } = await axios.get(url);
    buildFreshwaterFishingType(data);
  } catch (err) {
    console.error(err);
  }
}

function buildFreshwaterFishingType(html) {
  const dom = new JSDOM(html);
  const { document } = dom.window;

  const content = document.querySelector(".content-items .col-left");

  const headerNum = 2;

  const headerIndices = [...content.children].findIndex((child) => {
    return child.tagName === `H${headerNum}`;
  });

  const sections = headerIndices.map((i) => {
    const header = content.children[i];
    const section = {
      heading: header.textContent,
      text: scrapeSectionTextContent(content, i + 1),
      sections: [],
    };
  });
}

function scrapeSection(parent, index, headerNum = 2, text = []) {
  if (
    index >= section.children.length ||
    section.children[index].tagName === `H${headerNum + 1}`
  ) {
    return text.join("\n");
  }
  const textContent = section.children[index].textContent;
  text.push(textContent);
  return scrapeSection(section, index + 1, text);
}

function scrapeSectionTextContent(parent, index, text = []) {
  if (
    index >= parent.children.length ||
    parent.children[index].tagName.match(/h\d/i)
  ) {
    return text.join("\n");
  }
  const textContent = parent.children[index].textContent;
  text.push(textContent);
  return scrapeSectionTextContent(parent, index + 1, text);
}
