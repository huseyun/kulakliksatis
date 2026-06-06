import { router, useFocusEffect } from "expo-router";
import { useCallback, useState } from "react";
import { FlatList, StyleSheet, View } from "react-native";
import { Text, TextInput } from "react-native-paper";
import { getRecommended, ItemSummaryResponse } from "@/api/items";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";
import ProductCard from "@/components/features/ProductCard";

export default function ShopperHomeScreen() {
  const [items, setItems] = useState<ItemSummaryResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useFocusEffect(
    useCallback(() => {
      loadRecommended();
    }, [])
  );

  const loadRecommended = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getRecommended();
      setItems(data);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Ürünler yüklenemedi.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingScreen />;

  return (
    <FlatList
      style={styles.container}
      data={items}
      keyExtractor={(item) => String(item.id)}
      renderItem={({ item }) => (
        <ProductCard
          item={item}
          onPress={() => router.push(`/(shopper)/products/${item.id}`)}
        />
      )}
      ListHeaderComponent={
        <View style={styles.header}>
          {error && <ErrorMessage message={error} />}
          <Text variant="titleMedium" style={styles.sectionTitle}>🎧 Önerilen Ürünler</Text>
        </View>
      }
      ListEmptyComponent={
        <View style={styles.empty}>
          <Text style={styles.emptyText}>Henüz önerilen ürün yok.</Text>
        </View>
      }
      contentContainerStyle={styles.list}
    />
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#f5f5f5" },
  list: { paddingBottom: 24 },
  header: { paddingHorizontal: 16, paddingTop: 20, paddingBottom: 12 },
  sectionTitle: { color: "#1a237e", fontWeight: "bold" },
  empty: { alignItems: "center", marginTop: 60 },
  emptyText: { color: "#9ca3af" },
});
