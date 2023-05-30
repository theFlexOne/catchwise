import fs from "fs";
import { JSDOM } from "jsdom";

const html = fs.readFileSync(
  "./html/takemefishing/freshwater-lake-fishing.html",
  "utf8"
);

export default function scrapeFreshwaterLakeFishing() {
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
      sections: [],
    };

    section.sections = scrapeSections(document, heading + 1);
    sections.push(section);
  });

  return sections;
}

// export default function scrapeFreshwaterLakeFishing() {
//   const dom = new JSDOM(html);
//   const { document } = dom.window;

//   const h1 = document.querySelector("h1");

//   const title = h1.textContent;
//   const text = h1.nextElementSibling.textContent;

//   const scrapeSections = (headerNum = 2, sections = []) => {
//     const sectionInfo = {
//       heading: "",
//       text: [],
//       sections: [],
//     };

//     const headerIndices = [...content.children].filter((child) => {
//       return child.tagName === `H${headerNum}`;
//     });

//     if (headerIndices.length === 0) {
//       return sections;
//     }

//     const info = headerIndices.map((idx, i) => {
//       const endIdx = headerIndices[i + 1] || content.children.length;
//       return [...content.children].slice(idx, endIdx).reduce(
//         (acc, child, i) => {
//           if (i === 0) {
//             acc.heading = child.textContent;
//             return acc;
//           }
//           acc.text.push(child.textContent);
//           return acc;
//         },
//         { ...sectionInfo }
//       );
//     });
//     sections.push({
//       heading: info.heading,
//       text: info.text.join("\n"),
//       sections: scrapeSections(headerNum + 1, info.sections),
//     });

//     return sections;
//   };
//   return {
//     title,
//     text,
//     sections: scrapeSections(),
//   };
// }
