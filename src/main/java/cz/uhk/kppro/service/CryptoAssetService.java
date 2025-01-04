package cz.uhk.kppro.service;

import cz.uhk.kppro.model.CryptoAsset;
import java.util.List;

public interface CryptoAssetService {
    List<CryptoAsset> getAllCryptoAssets();
    CryptoAsset getCryptoAssetById(Integer id);
    CryptoAsset getCryptoAssetBySymbol(String symbol);
    List<CryptoAsset> searchCryptoAssetsByName(String name);
    void saveCryptoAsset(CryptoAsset cryptoAsset);
    void deleteCryptoAsset(Integer id);
}
