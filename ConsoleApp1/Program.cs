using System;
using System.IO;
using System.Net.Sockets;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace SenderApplication
{
    class Program
    {
        static async Task Main(string[] args)
        {
            await SendData();
        }

        static async Task SendData()
        {
            try
            {
                using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
                {
                    var publicKey = rsa.ExportParameters(false);

                    Console.WriteLine("Enter the text to sign:");
                    string textToSign = Console.ReadLine() ?? "";

                    byte[] signature = SignData(textToSign, rsa);

                    SignatureData dataToSend = new SignatureData
                    {
                        PublicKey = new RSAParametersData(publicKey.Modulus, publicKey.Exponent),
                        Text = textToSign,
                        Signature = signature
                    };

                    await SendDataToIntermediateServer(dataToSend);
                    Console.WriteLine("Data sent successfully.");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in SendData: {ex.Message}");
            }
        }

        static byte[] SignData(string data, RSACryptoServiceProvider rsa)
        {
            byte[] bytesToSign = Encoding.UTF8.GetBytes(data);
            return rsa.SignData(bytesToSign, HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);
        }

        static async Task SendDataToIntermediateServer(SignatureData data)
        {
            try
            {
                using (TcpClient client = new TcpClient("localhost", 3038))
                using (NetworkStream stream = client.GetStream())
                using (StreamWriter writer = new StreamWriter(stream))
                {
                    string jsonData = JsonSerializer.Serialize(data);
                    await writer.WriteAsync(jsonData);
                    await writer.FlushAsync();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in SendDataToIntermediateServer: {ex.Message}");
            }
        }
    }

    public class SignatureData
    {
        public byte[] Signature { get; set; }
        public string Text { get; set; }
        public RSAParametersData PublicKey { get; set; }
    }

    public class RSAParametersData
    {
        public byte[] Modulus { get; set; }
        public byte[] Exponent { get; set; }

        public RSAParametersData(byte[] modulus, byte[] exponent)
        {
            Modulus = modulus;
            Exponent = exponent;
        }
    }
}