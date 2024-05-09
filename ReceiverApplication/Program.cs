using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Security.Cryptography;
using System.Text.Json;
using System.Threading.Tasks;

namespace ReceiverApplication
{
    class Program
    {
        static async Task Main(string[] args)
        {
            await StartReceiver();
        }

        static async Task StartReceiver()
        {
            try
            {
                TcpListener listener = new TcpListener(IPAddress.Any, 4040);
                listener.Start();
                Console.WriteLine("Receiver started. Waiting for data...");

                while (true)
                {
                    using (TcpClient client = await listener.AcceptTcpClientAsync())
                    {
                        await HandleClient(client);
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in StartReceiver: {ex.Message}");
            }
        }

        static async Task HandleClient(TcpClient client)
        {
            try
            {
                using (StreamReader reader = new StreamReader(client.GetStream()))
                {
                    string jsonData = await reader.ReadToEndAsync();
                    SignatureData data = JsonSerializer.Deserialize<SignatureData>(jsonData);

                    bool signatureValid = VerifySignature(data);
                    if (signatureValid)
                    {
                        Console.WriteLine("Digital signature is valid.");
                    }
                    else
                    {
                        Console.WriteLine("Digital signature is not valid.");
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in HandleClient: {ex.Message}");
            }
        }

        static bool VerifySignature(SignatureData data)
        {
            try
            {
                RSAParameters rsaParams = new RSAParameters
                {
                    Modulus = data.PublicKey.Modulus,
                    Exponent = data.PublicKey.Exponent
                };

                using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
                {
                    rsa.ImportParameters(rsaParams);

                    byte[] bytesToVerify = System.Text.Encoding.UTF8.GetBytes(data.Text);

                    return rsa.VerifyData(bytesToVerify, data.Signature, HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in VerifySignature: {ex.Message}");
                return false;
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