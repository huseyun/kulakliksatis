import { router, useFocusEffect } from "expo-router";
import { useCallback, useState } from "react";
import { FlatList, StyleSheet, View } from "react-native";
import { FAB, Text } from "react-native-paper";
import { getMyItems } from "@/api/sellers";
import { ItemSummaryResponse } from "@/api/items";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";
import ProductCard from "@/components/features/ProductCard";

export default function SellerProductsScreen() {
  const [items, setItems] = useState<ItemSummaryResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useFocusEffect(
    useCallback(() => {
      loadItems();
    }, [])
  );

  const loadItems = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getMyItems();
      setItems(data);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Ürünler yüklenemedi.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingScreen />;

  return (
    <View style={styles.container}>
      {error && <ErrorMessage message={error} />}
      <FlatList
        data={items}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <ProductCard
            item={item}
            onPress={() => router.push(`/(seller)/products/${item.id}`)}
          />
        )}
        ListEmptyComponent={
          <View style={styles.empty}>
            <Text style={styles.emptyText}>Henüz ürün eklemediniz.</Text>
          </View>
        }
        contentContainerStyle={styles.list}
      />
      <FAB
        icon="plus"
        label="Ürün Ekle"
        style={styles.fab}
        onPress={() => router.push("/(seller)/products/create")}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#f5f5f5" },
  list: { paddingTop: 16, paddingBottom: 80 },
  empty: { alignItems: "center", marginTop: 60 },
  emptyText: { color: "#9ca3af" },
  fab: { position: "absolute", bottom: 24, right: 16, backgroundColor: "#ff6f00" },
});
