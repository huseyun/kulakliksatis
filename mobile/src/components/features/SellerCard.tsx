import React from "react";
import { Pressable, StyleSheet } from "react-native";
import { Card, Text } from "react-native-paper";
import { SellerResponse } from "@/api/sellers";

type Props = { seller: SellerResponse; onPress?: () => void };

export default function SellerCard({ seller, onPress }: Props) {
  return (
    <Pressable onPress={onPress} style={styles.wrapper}>
      <Card elevation={1}>
        <Card.Content>
          <Text variant="titleMedium">{seller.companyName ?? seller.username}</Text>
          <Text variant="bodySmall" style={styles.username}>@{seller.username}</Text>
          <Text variant="bodySmall" style={styles.email}>{seller.email}</Text>
        </Card.Content>
      </Card>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  wrapper: { marginHorizontal: 16, marginBottom: 12 },
  username: { color: "#6b7280", marginTop: 4 },
  email: { color: "#9ca3af" },
});
