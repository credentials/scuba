/*
 * $Id: $
 */

package net.sourceforge.scuba.simservice;

/**
 * GSM 11.11 constants. Based on ETSI TS 100 977 V8.10.0.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public interface GSM1111
{
    /**
     * Command APDU class byte.
     */
    static final byte CLA_GSM = (byte) 0xA0;

    /**
     * Command APDU instruction byte.
     */
    static final byte
    INS_SELECT = (byte) 0xA4,
    INS_STATUS = (byte) 0xF2,
    INS_READ_BINARY = (byte) 0xB0,
    INS_UPDATE_BINARY = (byte) 0xD6,
    INS_READ_RECORD = (byte) 0xB2,
    INS_UPDATE_RECORD = (byte) 0xDC,
    INS_SEEK = (byte) 0xA2,
    INS_INCREASE = (byte) 0x32,
    INS_VERIFY_CHV = (byte) 0x20,
    INS_CHANGE_CHV = (byte) 0x24,
    INS_DISABLE_CHV = (byte) 0x26,
    INS_ENABLE_CHV = (byte) 0x28,
    INS_UNBLOCK_CHV = (byte) 0x2C,
    INS_INVALIDATE = (byte) 0x04,
    INS_REHABILITATE = (byte) 0x44,
    INS_RUN_GSM_ALGORITHM = (byte) 0x88,
    INS_SLEEP = (byte) 0xFA,
    INS_GET_RESPONSE = (byte) 0xC0,
    INS_TERMINAL_PROFILE = (byte) 0x10,
    INS_ENVELOPE = (byte) 0xC2,
    INS_FETCH = (byte) 0x12,
    INS_TERMINAL_RESPONSE = (byte) 0x14;

    static final int
    SW_NO_ERROR = 0x9000,
    SW_INCORRECT_P3_00 = 0x6700,
    SW_INCORRECT_P1P2_00 = 0x6B00,
    SW_UNKNOWN_INS_BYTE_00 = 0x6D00,
    SW_UNKNOWN_CLA_BYTE_00 = 0x6E00,
    SW_TECHNICAL_PROBLEM_00 = 0x6F00,
    SW_NO_ERROR_AFTER_INTERNAL_UPDATE_0 = 0x9200,
    SW_MEMORY_PROBLEM = 0x9240,
    SW_NO_EF_SELECTED = 0x9400,
    SW_OUT_OF_RANGE = 0x9402,
    SW_INVALID_ADDRESS = 0x9402,
    SW_FILE_ID_NOT_FOUND = 0x9404,
    SW_PATTERN_NOT_FOUND = 0x9404,
    SW_FILE_INCONSISTENT_WITH_COMMAND = 0x9404,
    SW_ACCESS_CONDITION_NOT_FULFILLED = 0x9804,
    SW_UNSUCCESSFUL_CHV_VERIFICATION_ATTEMPTS_LEFT = 0x9804, // at least one attempt left
    SW_UNSUCCESSFUL_UNBLOCK_CHV_VERIFICATION_ATTEMPTS_LEFT = 0x9804, // at least one attempt left
    SW_AUTHENTICATION_FAILED = 0x9804,
    SW_IN_CONTRADICTION_WITH_CHV_STATUS = 0x9808,
    SW_IN_CONTRADICTION_WITH_INVALIDATION_STATUS = 0x9810,
    SW_UNSUCCESSFUL_CHV_VERIFICATION_NO_ATTEMPTS_LEFT = 0x9840,
    SW_UNSUCCESSFUL_UNBLOC_CHV_VERIFICATION_NO_ATTEMPTS_LEFT = 0x9840,
    SW_CHV_BLOCKED = 0x9840,
    SW_UNBLOCK_CHV_BLOCKED = 0x9840,
    SW_INCREASE_CANNOT_BE_PERFORMED = 0x9850;
    
    /**
     * GSM file identifier.
     * Based on Figure 8 on page 88 of ETSI TS 100 977.
     */
    static final short
    MF = 0x3F00,
    DF_GSM = 0x7F20,
        DF_IRIDIUM = 0x5F30, DF_GLOBST = 0x5F31, DF_ICO = 0x5F32, DF_ACES = 0x5F33,
        DF_PCS1900 = 0x5F40,
        EF_LP = 0x6F05, EF_IMSI = 0x6F07, EF_K_C = 0x6F20, EF_PLMN_SEL = 0x6F30, EF_HPLMN = 0x6F31, EF_ACM_MAX = 0x6F37,
        EF_SST = 0x6F38, EF_ACM = 0x6F39, EF_GID1 = 0x6F3E, EF_GID2 = 0x6F3F, EF_PUCT = 0x6F41, EF_CBMI = 0x6F45,
        EF_SPN = 0x6F46, EF_CBMID = 0x6F48, EF_BCCH = 0x6F74, EF_ACC = 0x6F78, EF_FPLMN = 0x6F7B, EF_LOCI = 0x6F7E,
        EF_AD = 0x6FAD, EF_PHASE = 0x6FAE, EF_VGCS = 0x6FB1, EF_VGCSS = 0x6FB2, EF_VBS = 0x6FB3, EF_VBSS = 0x6FB4,
        EF_E_MLPP = 0x6FB5, EF_AA_E_M = 0x6FB6, EF_ECC = 0x6FB7, EF_CBMIR = 0x6F50, EF_NIA = 0x6F51, EF_K_C_GPRS = 0x6F52,
        EF_LOCI_GPRS = 0x6F53,              
    DF_TELECOM = 0x7F10,
        EF_ADN = 0x6F3A, EF_FDN = 0x6F3B, EF_SMS = 0x6F3C,EF_CCP = 0x6F3D,EF_MSISDN = 0x6F40,
        EF_SMSP = 0x6F42, EF_SMSS = 0x6F43, EF_LND = 0x6F44, EF_SMSR = 0x6F47, EF_SND = 0x6F49,
        EF_EXT1 = 0x6F4A, EF_EXT2 = 0x6F4B, EF_EXT3 = 0x6F4C, EF_BDN = 0x6F4D, EF_EXT4 = 0x6F4E,
    DF_IS_41 = 0x7F22,
    DF_FP_CTS = 0x7F23,
    EF_ICCID = 0x2FE2, EF_ELP = 0x2F05;

    /**
     * GSM file (path) identifier.
     * Based on Figure 8 on page 88 of ETSI TS 100 977.
     */
    static final short[]
    PATH_MF = { MF },
    PATH_DF_GSM = { MF, DF_GSM},
        PATH_DF_IRIDIUM = { MF, DF_GSM, DF_IRIDIUM},
        PATH_DF_GLOBST = { MF, DF_GSM, DF_GLOBST },
        PATH_DF_ICO = { MF, DF_GSM, DF_ICO },
        PATH_DF_ACES = { MF, DF_GSM, DF_ACES },
        PATH_DF_PCS1900 = { MF, DF_GSM, DF_PCS1900 },
        PATH_EF_LP = { MF, DF_GSM, EF_LP },
        PATH_EF_IMSI = { MF, DF_GSM, EF_IMSI },
        PATH_EF_K_C = { MF, DF_GSM, EF_K_C },
        PATH_EF_PLMN_SEL = { MF, DF_GSM, EF_PLMN_SEL },
        PATH_EF_HPLMN = { MF, DF_GSM, EF_HPLMN },
        PATH_EF_ACM_MAX = { MF, DF_GSM, EF_ACM_MAX },
        PATH_EF_SST = { MF, DF_GSM, EF_SST },
        PATH_EF_ACM = { MF, DF_GSM, EF_ACM },
        PATH_EF_GID1 = { MF, DF_GSM, EF_GID1 },
        PATH_EF_GID2 = { MF, DF_GSM, EF_GID2 },
        PATH_EF_PUCT = { MF, DF_GSM, EF_PUCT },
        PATH_EF_CBMI = { MF, DF_GSM, EF_CBMI },
        PATH_EF_SPN = { MF, DF_GSM, EF_SPN },
        PATH_EF_CBMID = { MF, DF_GSM, EF_CBMID },
        PATH_EF_BCCH = { MF, DF_GSM, EF_BCCH },
        PATH_EF_ACC = { MF, DF_GSM, EF_ACC },
        PATH_EF_FPLMN = { MF, DF_GSM, EF_FPLMN },
        PATH_EF_LOCI = { MF, DF_GSM, EF_LOCI },
        PATH_EF_AD = { MF, DF_GSM, EF_AD },
        PATH_EF_PHASE = { MF, DF_GSM, EF_PHASE },
        PATH_EF_VGCS = { MF, DF_GSM, EF_VGCS },
        PATH_EF_VGCSS = { MF, DF_GSM, EF_VGCSS },
        PATH_EF_VBS = { MF, DF_GSM, EF_VBS },
        PATH_EF_VBSS = { MF, DF_GSM, EF_VBSS },
        PATH_EF_E_MLPP = { MF, DF_GSM, EF_E_MLPP },
        PATH_EF_AA_E_M = { MF, DF_GSM, EF_AA_E_M },
        PATH_EF_ECC = { MF, DF_GSM, EF_ECC },
        PATH_EF_CBMIR = { MF, DF_GSM, EF_CBMIR },
        PATH_EF_NIA = { MF, DF_GSM, EF_NIA },
        PATH_EF_K_C_GPRS = { MF, DF_GSM, EF_K_C_GPRS },
        PATH_EF_LOCI_GPRS = { MF, DF_GSM, EF_LOCI_GPRS },               
    PATH_DF_TELECOM = { MF, DF_TELECOM },
        PATH_EF_ADN = { MF, DF_TELECOM, EF_ADN },
        PATH_EF_FDN = { MF, DF_TELECOM, EF_FDN },
        PATH_EF_SMS = { MF, DF_TELECOM, EF_SMS },
        PATH_EF_CCP = { MF, DF_TELECOM, EF_CCP },
        PATH_EF_MSISDN = { MF, DF_TELECOM, EF_MSISDN },
        PATH_EF_SMSP = { MF, DF_TELECOM, EF_SMSP },
        PATH_EF_SMSS = { MF, DF_TELECOM, EF_SMSS },
        PATH_EF_LND = { MF, DF_TELECOM, EF_LND },
        PATH_EF_SMSR = { MF, DF_TELECOM, EF_SMSR },
        PATH_EF_SND = { MF, DF_TELECOM, EF_SND },
        PATH_EF_EXT1 = { MF, DF_TELECOM, EF_EXT1 },
        PATH_EF_EXT2 = { MF, DF_TELECOM, EF_EXT2 },
        PATH_EF_EXT3 = { MF, DF_TELECOM, EF_EXT3 },
        PATH_EF_BDN = { MF, DF_TELECOM, EF_BDN },
        PATH_EF_EXT4 = { MF, DF_TELECOM, EF_EXT4 },
    PATH_DF_IS_41 = { MF, DF_IS_41 },
    PATH_EF_ICCID = { MF, EF_ICCID },
    PATH_EF_ELP = { MF, EF_ELP };
    
    static final short[][] ALL_EF_PATHS = {
        PATH_EF_LP,
        PATH_EF_IMSI,
        PATH_EF_K_C,
        PATH_EF_PLMN_SEL,
        PATH_EF_HPLMN,
        PATH_EF_ACM_MAX,
        PATH_EF_SST,
        PATH_EF_ACM,
        PATH_EF_GID1,
        PATH_EF_GID2,
        PATH_EF_PUCT,
        PATH_EF_CBMI,
        PATH_EF_SPN,
        PATH_EF_CBMID,
        PATH_EF_BCCH,
        PATH_EF_ACC,
        PATH_EF_FPLMN,
        PATH_EF_LOCI,
        PATH_EF_AD,
        PATH_EF_PHASE,
        PATH_EF_VGCS,
        PATH_EF_VGCSS,
        PATH_EF_VBS,
        PATH_EF_VBSS,
        PATH_EF_E_MLPP,
        PATH_EF_AA_E_M,
        PATH_EF_ECC,
        PATH_EF_CBMIR,
        PATH_EF_NIA,
        PATH_EF_K_C_GPRS,
        PATH_EF_LOCI_GPRS,
        PATH_EF_ADN,
        PATH_EF_FDN,
        PATH_EF_SMS,
        PATH_EF_CCP,
        PATH_EF_MSISDN,
        PATH_EF_SMSP,
        PATH_EF_SMSS,
        PATH_EF_LND,
        PATH_EF_SMSR,
        PATH_EF_SND,
        PATH_EF_EXT1,
        PATH_EF_EXT2,
        PATH_EF_EXT3,
        PATH_EF_BDN,
        PATH_EF_EXT4,
        PATH_EF_ICCID,
        PATH_DF_IS_41,
        PATH_EF_ELP
    };
}
