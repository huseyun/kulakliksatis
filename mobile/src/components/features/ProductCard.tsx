import { Image } from "expo-image";
import React from "react";
import { Pressable, StyleSheet, View } from "react-native";
import { Card, Text } from "react-native-paper";
import { ItemSummaryResponse } from "@/api/items";

type Props = { item: ItemSummaryResponse; onPress?: () => void };

export default function ProductCard({ item, onPress }: Props) {
  return (
    <Pressable onPress={onPress} style={styles.wrapper}>
      <Card elevation={2}>
        {item.thumbnailImageUrl ? (
          <Image source={{ uri: item.thumbnailImageUrl }} style={styles.image} contentFit="cover" />
        ) : (
          <View style={styles.imagePlaceholder}>
            <Text style={styles.imagePlaceholderText}>Görsel yok</Text>
          </View>
        )}
        <Card.Content style={styles.content}>
          <Text variant="titleMedium" numberOfLines={2}>{item.title}</Text>
          {item.price != null && (
            <Text variant="bodyMedium" style={styles.price}>
              {item.price.toLocaleString("tr-TR", { style: "currency", currency: "TRY" })}
            </Text>
          )}
        </Card.Content>
      </Card>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  wrapper: { marginHorizontal: 16, marginBottom: 12 },
  image: { width: "100%", height: 160 },
  imagePlaceholder: { width: "100%", height: 160, backgroundColor: "#e5e7eb", alignItems: "center", justifyContent: "center" },
  imagePlaceholderText: { color: "#9ca3af", fontSize: 14 },
  content: { paddingTop: 12 },
  price: { color: "#ff6f00", marginTop: 4, fontWeight: "bold" },
});
