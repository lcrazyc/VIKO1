using System;
using System.Security.Cryptography;

namespace ReceiverApplication
{
    public static class SignatureHelper
    {
        public static bool VerifySignature(byte[] data, RSAParameters publicKey, byte[] signature)
        {
            try
            {
                using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
                {
                    rsa.ImportParameters(publicKey);

                    // Verify the signature
                    return rsa.VerifyData(data, signature, HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);
                }
            }
            catch (CryptographicException ex)
            {
                Console.WriteLine($"Error in VerifySignature: {ex.Message}");
                return false;
            }
        }
    }
}