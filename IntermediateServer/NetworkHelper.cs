using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text.Json;
using System.Threading.Tasks;

namespace IntermediateServer
{
    public static class NetworkHelper
    {
        public static async Task<string?> ReceiveDataAsync(TcpClient client)
        {
            try
            {
                using (StreamReader reader = new StreamReader(client.GetStream()))
                {
                    return await reader.ReadToEndAsync();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in ReceiveDataAsync: {ex.Message}");
                return null;
            }
        }

        public static async Task<bool> SendDataAsync(TcpClient client, string jsonData)
        {
            try
            {
                using (StreamWriter writer = new StreamWriter(client.GetStream()))
                {
                    await writer.WriteAsync(jsonData);
                    await writer.FlushAsync();
                    return true;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in SendDataAsync: {ex.Message}");
                return false;
            }
        }
    }

    
}