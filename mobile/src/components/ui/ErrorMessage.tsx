import React from "react";
import { StyleSheet, View } from "react-native";
import { Text } from "react-native-paper";

type Props = { message: string };

export default function ErrorMessage({ message }: Props) {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>{message}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { marginHorizontal: 16, marginTop: 8, borderRadius: 8, backgroundColor: "#fee2e2", paddingHorizontal: 16, paddingVertical: 12 },
  text: { color: "#b91c1c", fontSize: 14 },
});
