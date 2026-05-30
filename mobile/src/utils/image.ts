import { IMAGE_BUCKET, STORAGE_BASE_URL } from "@constants/config";

export function buildImageUrl(key: string | null | undefined): string | null {
  if (!key) return null;
  return `${STORAGE_BASE_URL}/${IMAGE_BUCKET}/${key}`;
}

export function getThumbnailUrl(
  images: Array<{ thumbnailKey?: string; originalKey?: string; isThumbnail?: boolean }> | null | undefined
): string | null {
  if (!images?.length) return null;
  const thumb = images.find((img) => img.isThumbnail);
  const key = thumb?.thumbnailKey ?? images[0]?.thumbnailKey ?? images[0]?.originalKey;
  return buildImageUrl(key ?? null);
}
