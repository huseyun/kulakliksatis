import * as ImagePicker from "expo-image-picker";
import { useLocalSearchParams } from "expo-router";
import { useState } from "react";
import { FlatList, StyleSheet, View } from "react-native";
import { Button, Chip, Text } from "react-native-paper";
import { uploadItemImages } from "@/api/items";
import ErrorMessage from "@/components/ui/ErrorMessage";
import { Image } from "expo-image";

type PickedFile = { uri: string; name: string; type: string };

export default function ProductImagesScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const [picked, setPicked] = useState<PickedFile[]>([]);
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [successMsg, setSuccessMsg] = useState<string | null>(null);

  const pickImages = async () => {
    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsMultipleSelection: true,
      quality: 0.8,
    });
    if (!result.canceled) {
      const files = result.assets.map((asset, i) => ({
        uri: asset.uri,
        name: asset.fileName ?? `image_${i}.jpg`,
        type: asset.mimeType ?? "image/jpeg",
      }));
      setPicked((prev) => [...prev, ...files]);
    }
  };

  const removeImage = (uri: string) => {
    setPicked((prev) => prev.filter((f) => f.uri !== uri));
  };

  const handleUpload = async () => {
    if (!id || picked.length === 0) return;
    setUploading(true);
    setError(null);
    setSuccessMsg(null);
    try {
      await uploadItemImages(Number(id), picked);
      setSuccessMsg(`${picked.length} görsel yüklendi.`);
      setPicked([]);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Yükleme başarısız.");
    } finally {
      setUploading(false);
    }
  };

  return (
    <View style={styles.container}>
      <FlatList
        data={picked}
        keyExtractor={(item) => item.uri}
        numColumns={3}
        ListHeaderComponent={
          <View style={styles.header}>
            <Text variant="bodyMedium" style={styles.hint}>
              Galeriden fotoğraf seçin, sonra yükleyin.
            </Text>
            <Button mode="outlined" icon="image-plus" onPress={pickImages} style={styles.pickButton}>
              Fotoğraf Seç
            </Button>
            {error && <ErrorMessage message={error} />}
            {successMsg && (
              <View style={styles.successBox}>
                <Text style={styles.successText}>{successMsg}</Text>
              </View>
            )}
          </View>
        }
        renderItem={({ item }) => (
          <View style={styles.imageWrapper}>
            <Image source={{ uri: item.uri }} style={styles.thumbnail} contentFit="cover" />
            <Chip
              compact
              onClose={() => removeImage(item.uri)}
              style={styles.chip}
              textStyle={styles.chipText}
            >
              {item.name.length > 10 ? item.name.slice(0, 10) + "…" : item.name}
            </Chip>
          </View>
        )}
        ListFooterComponent={
          picked.length > 0 ? (
            <Button
              mode="contained"
              icon="upload"
              onPress={handleUpload}
              loading={uploading}
              disabled={uploading}
              style={styles.uploadButton}
              contentStyle={styles.buttonContent}
            >
              {picked.length} Görseli Yükle
            </Button>
          ) : null
        }
        contentContainerStyle={styles.list}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#f5f5f5" },
  list: { padding: 16, paddingBottom: 40 },
  header: { marginBottom: 16 },
  hint: { color: "#6b7280", marginBottom: 12 },
  pickButton: { borderColor: "#1a237e" },
  imageWrapper: { flex: 1 / 3, margin: 4 },
  thumbnail: { width: "100%", aspectRatio: 1, borderRadius: 8 },
  chip: { marginTop: 4, backgroundColor: "#e0e7ff" },
  chipText: { fontSize: 10 },
  uploadButton: { marginTop: 16 },
  buttonContent: { paddingVertical: 4 },
  successBox: { backgroundColor: "#dcfce7", borderRadius: 8, padding: 12, marginTop: 8 },
  successText: { color: "#166534", fontSize: 14 },
});
