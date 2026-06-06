import { useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { ScrollView, StyleSheet, View } from "react-native";
import { Card, Chip, Divider, Text } from "react-native-paper";
import { getItem, ItemResponse } from "@/api/items";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";
import { buildImageUrl } from "@/utils/image";
import { Image } from "expo-image";

export default function ShopperProductDetailScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const [item, setItem] = useState<ItemResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;
    setLoading(true);
    getItem(Number(id))
      .then(setItem)
      .catch((e: unknown) => setError(e instanceof Error ? e.message : "Ürün yüklenemedi."))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <LoadingScreen />;

  const thumbnailUrl = item?.images?.length
    ? buildImageUrl(item.images.find(img => img.isThumbnail)?.thumbnailKey ?? item.images[0]?.thumbnailKey ?? null)
    : null;

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      {error && <ErrorMessage message={error} />}
      {item && (
        <>
          {thumbnailUrl ? (
            <Image source={{ uri: thumbnailUrl }} style={styles.image} contentFit="cover" />
          ) : (
            <View style={styles.imagePlaceholder}>
              <Text style={styles.imagePlaceholderText}>Görsel yok</Text>
            </View>
          )}

          <View style={styles.infoSection}>
            <Text variant="headlineSmall" style={styles.title}>{item.title}</Text>
            {item.price != null && (
              <Text variant="titleLarge" style={styles.price}>
                {item.price.toLocaleString("tr-TR", { style: "currency", currency: "TRY" })}
              </Text>
            )}

            {item.description ? (
              <>
                <Divider style={styles.divider} />
                <Text variant="bodyMedium" style={styles.description}>{item.description}</Text>
              </>
            ) : null}

            <Divider style={styles.divider} />

            <Card style={styles.sellerCard}>
              <Card.Content>
                <Text variant="bodySmall" style={styles.sellerLabel}>Satıcı</Text>
                <Text variant="bodyMedium">{item.seller?.companyName ?? item.seller?.username ?? "—"}</Text>
              </Card.Content>
            </Card>

            {item.autoeqId && (
              <Chip icon="equalizer" style={styles.recommendedChip} textStyle={styles.recommendedText}>
                AutoEQ Destekli
              </Chip>
            )}
          </View>
        </>
      )}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#f5f5f5" },
  content: { paddingBottom: 40 },
  image: { width: "100%", height: 260 },
  imagePlaceholder: { width: "100%", height: 200, backgroundColor: "#e5e7eb", alignItems: "center", justifyContent: "center" },
  imagePlaceholderText: { color: "#9ca3af" },
  infoSection: { padding: 20 },
  title: { color: "#1a237e", fontWeight: "bold", marginBottom: 8 },
  price: { color: "#ff6f00", fontWeight: "bold", marginBottom: 4 },
  divider: { marginVertical: 16 },
  description: { color: "#4b5563", lineHeight: 22 },
  sellerCard: { marginBottom: 12 },
  sellerLabel: { color: "#9ca3af", marginBottom: 2 },
  recommendedChip: { backgroundColor: "#fff3e0", alignSelf: "flex-start" },
  recommendedText: { color: "#ff6f00" },
});
