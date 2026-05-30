import React from "react";
import { StyleSheet, View } from "react-native";
import { Text } from "react-native-paper";

type Props = { title: string; phase: 2 | 3 };

export default function PlaceholderScreen({ title, phase }: Props) {
  return (
    <View style={styles.container}>
      <Text variant="titleLarge" style={styles.title}>{title}</Text>
      <Text variant="bodyMedium" style={styles.subtitle}>Phase {phase}'de tamamlanacak</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, alignItems: "center", justifyContent: "center", backgroundColor: "#f5f5f5" },
  title: { color: "#1a237e" },
  subtitle: { color: "#9ca3af", marginTop: 8 },
});
