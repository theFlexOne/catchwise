export default function buildUrl<T extends Record<string, string>>(
  root: string,
  searchParams?: T
) {
  const url = new URL(root);
  url.search = new URLSearchParams(searchParams).toString();
  return url.toString();
}
