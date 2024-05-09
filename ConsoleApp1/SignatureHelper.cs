using System;
using System.Security.Cryptography;

namespace SenderApplication
{
    public static class SignatureHelper
    {
        public static byte[] SignData(byte[] data, RSAParameters privateKey)
        {
            try
            {
                using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
                {
                    rsa.ImportParameters(privateKey);

                    // Compute digital signature
                    return rsa.SignData(data, HashAlgorithmName.SHA256);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in SignData: {ex.Message}");
                return null;
            }
        }
    }
}
