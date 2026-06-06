import { router } from "expo-router";
import { useState } from "react";
import { KeyboardAvoidingView, Platform, ScrollView, StyleSheet, View } from "react-native";
import { Button, HelperText, Text, TextInput } from "react-native-paper";
import { createItem } from "@/api/items";
import ErrorMessage from "@/components/ui/ErrorMessage";

export default function CreateProductScreen() {
  const [name, setName] = useState("");
  const [title, setTitle] = useState("");
  const [brand, setBrand] = useState("");
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleCreate = async () => {
    if (!name.trim() || !title.trim() || !brand.trim()) {
      setError("İsim, başlık ve marka zorunludur.");
      return;
    }
    setLoading(true);
    setError(null);
    try {
      await createItem({
        name: name.trim(),
        title: title.trim(),
        brand: brand.trim(),
        description: description.trim() || undefined,
      });
      router.back();
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Ürün oluşturulamadı.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView style={styles.flex} behavior={Platform.OS === "ios" ? "padding" : undefined}>
      <ScrollView contentContainerStyle={styles.scroll} keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          <TextInput label="Ürün İsmi" value={name} onChangeText={setName} mode="outlined" style={styles.input} left={<TextInput.Icon icon="tag" />} />
          <TextInput label="Başlık" value={title} onChangeText={setTitle} mode="outlined" style={styles.input} left={<TextInput.Icon icon="format-title" />} />
          <TextInput label="Marka" value={brand} onChangeText={setBrand} mode="outlined" style={styles.input} left={<TextInput.Icon icon="trademark" />} />
          <TextInput label="Açıklama (opsiyonel)" value={description} onChangeText={setDescription} mode="outlined" style={styles.input} multiline numberOfLines={3} />

          <HelperText type="info" visible>
            💡 Fiyat alanı henüz API'de desteklenmiyor.
          </HelperText>

          {error && <ErrorMessage message={error} />}

          <Button mode="contained" onPress={handleCreate} loading={loading} disabled={loading} style={styles.button} contentStyle={styles.buttonContent}>
            Ürün Oluştur
          </Button>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  flex: { flex: 1, backgroundColor: "#f5f5f5" },
  scroll: { flexGrow: 1 },
  container: { padding: 20 },
  input: { marginBottom: 12 },
  button: { marginTop: 8 },
  buttonContent: { paddingVertical: 4 },
});
