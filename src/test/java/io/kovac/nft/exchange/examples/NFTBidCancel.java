package io.kovac.nft.exchange.examples;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.kovac.nft.exchange.crypto.builders.NFTAuctionCancelBuilder;
import io.kovac.nft.exchange.crypto.builders.NFTBidCancelBuilder;
import org.arkecosystem.client.Connection;
import org.arkecosystem.crypto.configuration.Network;
import org.arkecosystem.crypto.identities.Address;
import org.arkecosystem.crypto.networks.Testnet;
import org.arkecosystem.crypto.transactions.types.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NFTBidCancel {

    public static long getNonce(Connection connection, String senderWallet) throws IOException {
        return Long.valueOf (((Map<String, Object>) connection.api().wallets.show(senderWallet).get("data")).get("nonce").toString());
    }

    public static void main(String[] args) throws IOException {
        Network.set(new Testnet());

        Map<String, Object> map = new HashMap<>();
        map.put("host", "http://localhost:4003/api/");
        map.put("content-type","application/json");

        Connection connection = new Connection(map);

        long nonce = getNonce(connection, Address.fromPassphrase("venue below waste gather spin cruise title still boost mother flash tuna")) + 1;

        List payload = new ArrayList<>();

        Transaction transaction = new NFTBidCancelBuilder()
                .nonce(nonce)
                .NFTBidCancelAsset("9d47440c54adaf6dcdef139db8f4e89a8fb40899e2e2931fad31c349406cc20f")
                .sign("venue below waste gather spin cruise title still boost mother flash tuna")
                .transaction;


        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Formatted Json");
        System.out.println(GSON.toJson(JsonParser.parseString(transaction.toJson())));

        payload.add(transaction.toHashMap());

        System.out.println("Payload response");
        Map<String, Object> postResponse = connection.api().transactions.create(payload);
        System.out.println(postResponse);
    }
}
