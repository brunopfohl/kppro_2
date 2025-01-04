package cz.uhk.kppro.service;

import cz.uhk.kppro.model.CryptoAsset;
import cz.uhk.kppro.repository.CryptoAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoAssetServiceImpl implements CryptoAssetService {
    private final CryptoAssetRepository cryptoAssetRepository;

    @Autowired
    public CryptoAssetServiceImpl(CryptoAssetRepository cryptoAssetRepository) {
        this.cryptoAssetRepository = cryptoAssetRepository;
    }

    @Override
    public List<CryptoAsset> getAllCryptoAssets() {
        return cryptoAssetRepository.findAll();
    }

    @Override
    public CryptoAsset getCryptoAssetById(Integer id) {
        return cryptoAssetRepository.findById(id).orElse(null);
    }

    @Override
    public CryptoAsset getCryptoAssetBySymbol(String symbol) {
        return cryptoAssetRepository.findBySymbol(symbol).orElse(null);
    }

    @Override
    public List<CryptoAsset> searchCryptoAssetsByName(String name) {
        return cryptoAssetRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public void saveCryptoAsset(CryptoAsset cryptoAsset) {
        cryptoAssetRepository.save(cryptoAsset);
    }

    @Override
    public void deleteCryptoAsset(Integer id) {
        cryptoAssetRepository.deleteById(id);
    }
}
