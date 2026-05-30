import { router, useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { FlatList, StyleSheet, View } from "react-native";
import { Button, Card, Chip, Divider, Text } from "react-native-paper";
import { getSellerById, SellerDetailedResponse } from "@/api/sellers";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";

export default function AdminSellerDetailScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const [seller, setSeller] = useState<SellerDetailedResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;
    loadSeller();
  }, [id]);

  const loadSeller = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getSellerById(Number(id));
      setSeller(data);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Satıcı yüklenemedi.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingScreen />;

  return (
    <FlatList
      style={styles.container}
      contentContainerStyle={styles.content}
      data={seller?.items ?? []}
      keyExtractor={(item) => String(item.id)}
      ListHeaderComponent={
        <>
          {error && <ErrorMessage message={error} />}
          {seller && (
            <Card style={styles.card}>
              <Card.Content>
                <Text variant="headlineSmall" style={styles.company}>
                  {seller.companyName ?? seller.username}
                </Text>
                <Divider style={styles.divider} />
                <View style={styles.row}>
                  <Text variant="bodySmall" style={styles.label}>Kullanıcı Adı</Text>
                  <Text variant="bodyMedium">@{seller.username}</Text>
                </View>
                <View style={styles.row}>
                  <Text variant="bodySmall" style={styles.label}>E-posta</Text>
                  <Text variant="bodyMedium">{seller.email}</Text>
                </View>
                <View style={styles.row}>
                  <Text variant="bodySmall" style={styles.label}>ID</Text>
                  <Text variant="bodyMedium">#{seller.id}</Text>
                </View>
              </Card.Content>
            </Card>
          )}

          <View style={styles.productsHeader}>
            <Text variant="titleMedium" style={styles.sectionTitle}>
              Ürünler ({seller?.items?.length ?? 0})
            </Text>
          </View>
        </>
      }
      renderItem={({ item }) => (
        <Card style={styles.itemCard}>
          <Card.Content style={styles.itemContent}>
            <Text variant="bodyMedium" numberOfLines={1}>{item.title}</Text>
            <Chip compact style={styles.priceChip} textStyle={styles.priceText}>
              {item.price != null
                ? item.price.toLocaleString("tr-TR", { style: "currency", currency: "TRY" })
                : "Fiyat yok"}
            </Chip>
          </Card.Content>
        </Card>
      )}
      ListEmptyComponent={
        <View style={styles.empty}>
          <Text style={styles.emptyText}>Bu satıcının ürünü yok.</Text>
        </View>
      }
      ListFooterComponent={
        <Button mode="outlined" icon="delete" textColor="#b91c1c" style={styles.deleteButton} onPress={() => router.push(`/(admin)/users/${id}`)}>
          Kullanıcıyı Görüntüle / Sil
        </Button>
      }
    />
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#f5f5f5" },
  content: { padding: 16, paddingBottom: 40 },
  card: { marginBottom: 16 },
  company: { color: "#1a237e", fontWeight: "bold" },
  divider: { marginVertical: 12 },
  row: { marginBottom: 10 },
  label: { color: "#9ca3af", marginBottom: 2 },
  productsHeader: { marginBottom: 12 },
  sectionTitle: { color: "#1a237e", fontWeight: "bold" },
  itemCard: { marginBottom: 8 },
  itemContent: { flexDirection: "row", justifyContent: "space-between", alignItems: "center" },
  priceChip: { backgroundColor: "#fff3e0" },
  priceText: { color: "#ff6f00", fontSize: 12 },
  empty: { alignItems: "center", paddingVertical: 24 },
  emptyText: { color: "#9ca3af" },
  deleteButton: { marginTop: 24, borderColor: "#b91c1c" },
});
