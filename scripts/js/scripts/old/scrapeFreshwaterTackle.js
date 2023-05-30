import { JSDOM } from "jsdom";
import fs from "fs";

const html = fs.readFileSync(
  "./html/takemefishing/freshwater-tackle.html",
  "utf8"
);

export default function scrapeFreshwaterTackle() {
  const dom = new JSDOM(html, { includeNodeLocations: true });
  const { document } = dom.window;

  const h1 = document.querySelector("h1");
  const title = h1.textContent.trim();
  const text = scrapeSectionTextContent(h1);
  const sections = scrapeSections(document);

  return {
    title,
    text,
    sections,
  };
}

function scrapeSectionTextContent(header) {
  const text = [];
  let node = header.nextElementSibling;

  while (node) {
    if (node.tagName.match(/h\d/i)) {
      break;
    }
    const textContent = node.textContent.trim();
    textContent.length && text.push(textContent);
    node = node.nextElementSibling;
  }

  return text.join("\n");
}

function scrapeSections(document, heading = 2, sections = []) {
  const sectionHeaders = [...document.querySelectorAll(`h${heading}`)];

  sectionHeaders.forEach((header) => {
    const section = {
      heading: header.textContent.trim(),
      text: scrapeSectionTextContent(header),
      tag: header.tagName,
      sections: [],
    };

    section.sections = scrapeSections(document, heading + 1);
    sections.push(section);
  });

  return sections;
}
