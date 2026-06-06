import { router, useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { KeyboardAvoidingView, Platform, ScrollView, StyleSheet, View } from "react-native";
import { Button, Dialog, Divider, Portal, Text, TextInput } from "react-native-paper";
import { deleteItem, getItem, ItemResponse, updateItem } from "@/api/items";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";
import { buildImageUrl } from "@/utils/image";
import { Image } from "expo-image";

export default function SellerProductDetailScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const [item, setItem] = useState<ItemResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [successMsg, setSuccessMsg] = useState<string | null>(null);
  const [deleteDialog, setDeleteDialog] = useState(false);
  const [deleting, setDeleting] = useState(false);
  const [editMode, setEditMode] = useState(false);

  const [name, setName] = useState("");
  const [title, setTitle] = useState("");
  const [brand, setBrand] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    loadItem();
  }, [id]);

  const loadItem = async () => {
    if (!id) return;
    setLoading(true);
    setError(null);
    try {
      const data = await getItem(Number(id));
      setItem(data);
      setName(data.title);
      setTitle(data.title);
      setBrand("");
      setDescription(data.description ?? "");
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Ürün yüklenemedi.");
    } finally {
      setLoading(false);
    }
  };

  const handleSave = async () => {
    if (!name.trim() || !title.trim()) {
      setError("İsim ve başlık zorunludur.");
      return;
    }
    setSaving(true);
    setError(null);
    setSuccessMsg(null);
    try {
      await updateItem(Number(id), { name: name.trim(), title: title.trim(), brand: brand.trim() || "—", description: description.trim() || undefined });
      setSuccessMsg("Ürün güncellendi.");
      setEditMode(false);
      loadItem();
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Güncelleme başarısız.");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async () => {
    if (!id) return;
    setDeleting(true);
    try {
      await deleteItem(Number(id));
      router.back();
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Silme başarısız.");
      setDeleteDialog(false);
    } finally {
      setDeleting(false);
    }
  };

  if (loading) return <LoadingScreen />;

  const thumbnailUrl = item?.images?.length
    ? buildImageUrl(item.images.find(img => img.isThumbnail)?.thumbnailKey ?? item.images[0]?.thumbnailKey ?? null)
    : null;

  return (
    <KeyboardAvoidingView style={styles.flex} behavior={Platform.OS === "ios" ? "padding" : undefined}>
      <ScrollView contentContainerStyle={styles.scroll} keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          {error && <ErrorMessage message={error} />}

          {thumbnailUrl ? (
            <Image source={{ uri: thumbnailUrl }} style={styles.image} contentFit="cover" />
          ) : (
            <View style={styles.imagePlaceholder}>
              <Text style={styles.imagePlaceholderText}>Görsel yok</Text>
            </View>
          )}

          <Button mode="outlined" icon="image-plus" style={styles.imageButton} onPress={() => router.push(`/(seller)/products/${id}/images`)}>
            Görsel Yönet
          </Button>

          <Divider style={styles.divider} />

          {!editMode ? (
            <>
              <Text variant="headlineSmall" style={styles.itemTitle}>{item?.title}</Text>
              {item?.price != null && (
                <Text variant="bodyLarge" style={styles.price}>
                  {item.price.toLocaleString("tr-TR", { style: "currency", currency: "TRY" })}
                </Text>
              )}
              {item?.description ? (
                <Text variant="bodyMedium" style={styles.description}>{item.description}</Text>
              ) : null}

              {successMsg && (
                <View style={styles.successBox}>
                  <Text style={styles.successText}>{successMsg}</Text>
                </View>
              )}

              <Button mode="contained" icon="pencil" onPress={() => setEditMode(true)} style={styles.editButton} contentStyle={styles.buttonContent}>
                Düzenle
              </Button>
              <Button mode="outlined" icon="delete" textColor="#b91c1c" style={styles.deleteButton} onPress={() => setDeleteDialog(true)}>
                Ürünü Sil
              </Button>
            </>
          ) : (
            <>
              <Text variant="titleMedium" style={styles.sectionTitle}>Ürünü Düzenle</Text>
              <TextInput label="İsim" value={name} onChangeText={setName} mode="outlined" style={styles.input} />
              <TextInput label="Başlık" value={title} onChangeText={setTitle} mode="outlined" style={styles.input} />
              <TextInput label="Marka" value={brand} onChangeText={setBrand} mode="outlined" style={styles.input} />
              <TextInput label="Açıklama" value={description} onChangeText={setDescription} mode="outlined" style={styles.input} multiline numberOfLines={3} />

              <Button mode="contained" onPress={handleSave} loading={saving} disabled={saving} style={styles.editButton} contentStyle={styles.buttonContent}>
                Kaydet
              </Button>
              <Button mode="text" onPress={() => setEditMode(false)} style={styles.cancelButton}>
                İptal
              </Button>
            </>
          )}
        </View>
      </ScrollView>

      <Portal>
        <Dialog visible={deleteDialog} onDismiss={() => setDeleteDialog(false)}>
          <Dialog.Title>Ürünü Sil</Dialog.Title>
          <Dialog.Content>
            <Text>Bu ürünü silmek istediğinize emin misiniz? Bu işlem geri alınamaz.</Text>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={() => setDeleteDialog(false)}>İptal</Button>
            <Button textColor="#b91c1c" onPress={handleDelete} loading={deleting} disabled={deleting}>Sil</Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  flex: { flex: 1, backgroundColor: "#f5f5f5" },
  scroll: { flexGrow: 1 },
  container: { padding: 16 },
  image: { width: "100%", height: 200, borderRadius: 12, marginBottom: 12 },
  imagePlaceholder: { width: "100%", height: 160, borderRadius: 12, backgroundColor: "#e5e7eb", alignItems: "center", justifyContent: "center", marginBottom: 12 },
  imagePlaceholderText: { color: "#9ca3af" },
  imageButton: { marginBottom: 8, borderColor: "#1a237e" },
  divider: { marginVertical: 16 },
  itemTitle: { color: "#1a237e", fontWeight: "bold", marginBottom: 4 },
  price: { color: "#ff6f00", fontWeight: "bold", marginBottom: 8 },
  description: { color: "#6b7280", marginBottom: 16 },
  sectionTitle: { color: "#1a237e", fontWeight: "bold", marginBottom: 12 },
  input: { marginBottom: 12 },
  editButton: { marginTop: 8 },
  deleteButton: { marginTop: 8, borderColor: "#b91c1c" },
  cancelButton: { marginTop: 4 },
  buttonContent: { paddingVertical: 4 },
  successBox: { backgroundColor: "#dcfce7", borderRadius: 8, padding: 12, marginBottom: 8 },
  successText: { color: "#166534", fontSize: 14 },
});
