import { router, useFocusEffect } from "expo-router";
import { useCallback, useState } from "react";
import { FlatList, StyleSheet, View } from "react-native";
import { FAB, Text } from "react-native-paper";
import { getSellers, SellerResponse } from "@/api/sellers";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";
import SellerCard from "@/components/features/SellerCard";

export default function AdminSellersScreen() {
  const [sellers, setSellers] = useState<SellerResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useFocusEffect(
    useCallback(() => {
      loadSellers();
    }, [])
  );

  const loadSellers = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getSellers();
      setSellers(data);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Satıcılar yüklenemedi.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingScreen />;

  return (
    <View style={styles.container}>
      {error && <ErrorMessage message={error} />}
      <FlatList
        data={sellers}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <SellerCard
            seller={item}
            onPress={() => router.push(`/(admin)/sellers/${item.id}`)}
          />
        )}
        ListEmptyComponent={
          <View style={styles.empty}>
            <Text style={styles.emptyText}>Satıcı bulunamadı.</Text>
          </View>
        }
        contentContainerStyle={styles.list}
      />
      <FAB
        icon="plus"
        label="Satıcı Ekle"
        style={styles.fab}
        onPress={() => router.push("/(admin)/sellers/create")}
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
